package com.seckill.controller;

import com.seckill.domain.SeckillUser;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.RedisService;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.vo.GoodsDetailVO;
import com.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Albert on 2020/3/9.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,
                         Model model, SeckillUser user) {
        if (user == null)
            return "login";
        model.addAttribute("user", user);
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsList);
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html))
            return html;
        String htmlName = "goods_list";
        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process(htmlName, ctx);
        if (!StringUtils.isEmpty(html))
            redisService.set(GoodsKey.goodsList, "", html);
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> toDetail(Model model, SeckillUser user, @PathVariable("goodsId") long goodsId) {
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        model.addAttribute("goods", goodsVO);

        long startAt = goodsVO.getStartTime().getTime();
        long endAt = goodsVO.getEndTime().getTime();
        long now = System.currentTimeMillis();
        // >0-等待时间 <0-已结束 =0-正在秒杀
        int seckillStatus;
        // 0-倒计时 1-正在秒杀 2-已结束
        int remainSeconds = 0;

        if (now < startAt) {
            seckillStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
        }

        GoodsDetailVO detail = new GoodsDetailVO(user, goodsVO, seckillStatus, remainSeconds);
        return Result.success(detail);
    }
}
