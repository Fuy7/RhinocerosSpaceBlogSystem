package com.fuy.blog.utils;

/**
 * @author fuy
 * @create 2020-06-27 22:35
 */
public interface Constants {

    interface User{
        String ROLE_ADMIN = "role_admin";
        String ROLE_ORDINARY = "role_ordinary";
        String DEFAULT_AVATAR = "https://img-blog.csdnimg.cn/20200627224048673.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDgyMTM4Nw==,size_16,color_FFFFFF,t_70";
        String DEFAULT_STATE = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content";
        String KEY_EMAIL_CONTENT = "key_email_content";
        String KEY_SEND_EMAIL_IP = "key_send_email_ip";
        String KEY_SEND_EMAIL_ADDRESS = "key_send_email_address";
    }

    interface Setting{
        String ADMIN_INIT_STATE = "admin_init_state";
    }
}
