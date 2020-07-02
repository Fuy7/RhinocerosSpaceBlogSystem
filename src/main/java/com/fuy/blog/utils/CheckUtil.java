package com.fuy.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fuy
 * @create 2020-07-01 17:46
 */
public class CheckUtil {

    private static final  String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static Boolean isEmail(String email){

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
