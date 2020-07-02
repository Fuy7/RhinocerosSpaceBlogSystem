package com.fuy.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuy.blog.entity.Setting;
import com.fuy.blog.entity.User;
import com.fuy.blog.service.SettingsService;
import com.fuy.blog.utils.Constants;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private SettingsService settingsService;

    @Test
    void contextLoads() {
        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.eq("s_key","password");
        Setting one = settingsService.getOne(wrapper);
        System.out.println(one);

    }




/*    @PostMapping("/login")
    public JsonResult login(String username,String password,String verCode){
        // 获取session中的验证码
        String sessionCode = request.getSession().getAttribute("captcha");
        // 判断验证码
        if (verCode==null || !sessionCode.equals(verCode.trim().toLowerCase())) {
            return JsonResult.error("验证码不正确");
        }

    }*/

}
