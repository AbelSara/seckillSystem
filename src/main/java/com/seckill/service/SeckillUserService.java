package com.seckill.service;

import com.seckill.dao.SeckillUserDao;
import com.seckill.domain.SeckillUser;
import com.seckill.exception.GlobalException;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillUserKey;
import com.seckill.result.CodeMsg;
import com.seckill.util.MD5Util;
import com.seckill.util.UUIDUtil;
import com.seckill.vo.LoginVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Albert on 2020/3/8.
 */
@Service
public class SeckillUserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;

    public SeckillUser getById(long id) {
        SeckillUser user = redisService.get(SeckillUserKey.ID, String.valueOf(id), SeckillUser.class);
        if (user != null)
            return user;
        user = seckillUserDao.getById(id);
        if (user != null)
            redisService.set(SeckillUserKey.ID, String.valueOf(id), user);
        return user;
    }

    public boolean updatePass(long id, String pass, String token) throws GlobalException {
        SeckillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String dbPass = MD5Util.formPassToDBPass(pass, user.getSalt());
        seckillUserDao.updatePass(id, dbPass);
        redisService.delete(SeckillUserKey.ID, String.valueOf(id));
        //双写一致性
        user.setPassword(dbPass);
        redisService.set(SeckillUserKey.TOKEN, token, user);
        return true;
    }

    public boolean login(HttpServletResponse response, LoginVO loginVO) throws GlobalException {
        if (loginVO == null) {
            throw new GlobalException(CodeMsg.ERROR_MSG);
        }
        String mobile = loginVO.getMobile();
        String formPass = loginVO.getPassword();
        SeckillUser user = getById(Long.parseLong(mobile));
        if (user == null)
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        if (!dbPass.equals(MD5Util.formPassToDBPass(formPass, dbSalt)))
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        //generate token
        redisService.set(SeckillUserKey.TOKEN, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.TOKEN.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token))
            return null;
        SeckillUser user = redisService.get(SeckillUserKey.TOKEN, token, SeckillUser.class);
        if (user != null)
            addCookie(response, token, user);
        return user;
    }
}
