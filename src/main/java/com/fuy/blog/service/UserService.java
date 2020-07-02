package com.fuy.blog.service;

import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
public interface UserService extends IService<User> {

    Result initAdmin(User user, HttpServletRequest request);

    void getCaptcha(HttpServletResponse response, String captchaKey);

    Result sendEmail(HttpServletRequest request, String email,String type);

    Result registerUser(User user, String verifyCode, String captchaCode,String captchaKey, HttpServletRequest request);
}
