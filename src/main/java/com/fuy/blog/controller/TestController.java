package com.fuy.blog.controller;

import com.fuy.blog.common.result.Result;
import com.fuy.blog.utils.Constants;
import com.fuy.blog.utils.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;

/**
 * @author fuy
 * @create 2020-07-01 15:27
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/captcha/{id}")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // 验证码存入session
        request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
        String captcha = (String)request.getSession().getAttribute("captcha");
        System.out.println("=====================>"+captcha);
        //存储到redis中
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT,captcha,60*10);

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @GetMapping("getKey")
    public Result getKey(){
        String key_captcha_content =(String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT);
        System.out.println("redis===============redis" + key_captcha_content);
        return Result.ok();
    }
}
