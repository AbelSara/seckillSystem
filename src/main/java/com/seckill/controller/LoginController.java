package com.seckill.controller;


import com.seckill.exception.GlobalException;
import com.seckill.rabbitmq.MQSender;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.SeckillUserService;
import com.seckill.util.ValidatorUtil;
import com.seckill.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Albert on 2020/3/9.
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SeckillUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVO loginVO) throws GlobalException {
        logger.info(loginVO.toString());
        //login
        boolean login = userService.login(response, loginVO);
        logger.info("login success.");
        return Result.success(login);
    }
}
