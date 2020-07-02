package com.fuy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Setting;
import com.fuy.blog.entity.User;
import com.fuy.blog.mapper.UserMapper;
import com.fuy.blog.service.SettingsService;
import com.fuy.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuy.blog.utils.CheckUtil;
import com.fuy.blog.utils.Constants;
import com.fuy.blog.utils.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Random random;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TaskService taskService;


    /**
    *@Description 初始化管理员
    *@Param
    *@Return
    *@Author Mr.Ren
    *@Date 2020/7/2
    *@Time 8:50
    */
    @Override
    public Result initAdmin(User user, HttpServletRequest request) {

        QueryWrapper<Setting> settingsQueryWrapper = new QueryWrapper<>();
        settingsQueryWrapper.eq("s_key",Constants.Setting.ADMIN_INIT_STATE);

        //查看是够被初始化
        if(settingsService.getOne(settingsQueryWrapper) != null){
            return Result.fail().message("已经进行过初始化");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        User u = baseMapper.selectOne(wrapper);

        if(u==null){
            //设置身份
            user.setRoles(Constants.User.ROLE_ADMIN);
            //设置头像
            user.setAvatar(Constants.User.DEFAULT_AVATAR);
            //设置初始化状态
            user.setState(Constants.User.DEFAULT_STATE);
            //设置注册IP地址
            user.setRegIp(request.getRemoteAddr());
            user.setLoginIp(request.getLocalAddr());
            //获取源密码
            String password = user.getPassword();
            //进行加密
            String encode = bCryptPasswordEncoder.encode(password);
            //保存至数据库
            user.setPassword(encode);
            int insert = baseMapper.insert(user);
            if(insert > 0){
                //更新标记
                user.setSign("1");
                Setting setting = new Setting();
                setting.setSKey(Constants.Setting.ADMIN_INIT_STATE);
                setting.setSValue("1");
                settingsService.save(setting);

                return Result.ok().message("管理员初始化成功");
            }
        }

        return Result.fail().message("用户已存在");
    }

    private static final String[] arrayFontTypes = {
            "Captcha.FONT_1",
            "Captcha.FONT_2",
            "Captcha.FONT_3",
            "Captcha.FONT_4",
            "Captcha.FONT_5",
            "Captcha.FONT_6",
            "Captcha.FONT_7",
            "Captcha.FONT_8",
            "Captcha.FONT_9",
            "Captcha.FONT_10",
    };


    /**
     *@Description 获取图灵验证码
     *@Param [response, captchaKey]
     *@Return void
     *@Author Mr.Ren
     *@Date 2020/7/2
     *@Time 8:50
     */
    @Override
    public void getCaptcha(HttpServletResponse response, String captchaKey) {

        if(StringUtils.isEmpty(captchaKey) || captchaKey.length() < 13){
            return;
        }

        long key = 0L;
        key = Long.parseLong(captchaKey);

        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int captchaType = random.nextInt(3);
        Captcha specCaptcha = null;
        if(captchaType == 0){
            // 三个参数分别为宽、高、位数
            specCaptcha = new SpecCaptcha(130, 48, 5);
        }
        else if(captchaType == 1){
            // gif类型
            specCaptcha = new GifCaptcha(130, 48);
        }
        else if(captchaType == 2){
            // 算术类型
            specCaptcha = new ArithmeticCaptcha(130, 48);
            specCaptcha.text();  // 获取运算的结果：5
        }
        int index = random.nextInt(arrayFontTypes.length);
        // 设置字体
        specCaptcha.setFont(Font.getFont(arrayFontTypes[index]));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        //获取生成的校验码
        String content = specCaptcha.text().toLowerCase();
        System.out.println("--------------------"+content+"--------------------");
        //存入redis
        boolean set = redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 10 * 60);
        // 输出图片流
        try {
            specCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *@Description  发送邮件
     *@Param
     *@Return
     *@Author Mr.Ren
     *@Date 2020/7/2
     *@Time 8:50
     */
    @Override
    public Result sendEmail(HttpServletRequest request, String email, String type) {
        //通过type判断操作类 : 注册 修改邮箱 找回密码
            if("register".equals(type) || "update".equals(type)){
            //注册 和 修改邮箱 如果注册过就报错
            //检测是否已经注册过
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email",email);
            User one = baseMapper.selectOne(userQueryWrapper);
            if(one!=null){
                //已经注册过了
                return Result.fail().message("该邮箱已经注册过");
            }
        }else if("forget".equals(type)){
            //找回密码
            //检测是否已经注册过
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email",email);
            User one = baseMapper.selectOne(userQueryWrapper);
            if(one == null){
                //已经注册过了
                return Result.fail().message("该邮箱未注册过");
            }
        }

        //防止恶意发送邮件, 同一个邮箱最多30秒一次,同一个邮箱一小时内10次
        String remoteAddr = request.getRemoteAddr();
        if(!StringUtils.isEmpty(remoteAddr)){
            remoteAddr = remoteAddr.replaceAll(":","_");
        }
        System.out.println(remoteAddr);
        //IP 获取发送次数
        Integer ipSendTime =(Integer) redisUtil.get(Constants.User.KEY_SEND_EMAIL_IP + remoteAddr);
        if(ipSendTime!=null && ipSendTime > 10){
            return Result.fail().message("操作过于频繁");
        }
        //邮箱地址
        Object hasSend = redisUtil.get(Constants.User.KEY_SEND_EMAIL_ADDRESS + email);
        if(hasSend!=null){
            return Result.fail().message("操作过于频繁");
        }

        //校验邮箱地址
        Boolean isEmail = CheckUtil.isEmail(email);
        if(!isEmail){
            return Result.fail().message("邮箱格式有误");
        }
        //发送验证码
        //生成随机4位验证码
        int r = random.nextInt(8999)+ 1000;
        System.out.println("--------------code:---------"+r);
        try {
            taskService.sendEmailVerifyCode(r+"",email);
        } catch (Exception e) {
            return Result.fail().message("验证码发送失败");
        }
        //向redis中添加IP发送次数的记录
        if(ipSendTime == null){
            ipSendTime = 0;
        }
        ipSendTime++;
        redisUtil.set(Constants.User.KEY_SEND_EMAIL_IP + remoteAddr,ipSendTime,60*60);
        //向redis中添加邮箱地址已经发送记录
        redisUtil.set(Constants.User.KEY_SEND_EMAIL_ADDRESS + email,"true",30);
        //向redis中添加发送的验证码
        redisUtil.set(Constants.User.KEY_EMAIL_CONTENT + email,r+"",60*10);
        return Result.ok().message("邮箱验证码发送成功");
    }


    /**
     *@Description 注册用户
     *@Param
     *@Return
     *@Author Mr.Ren
     *@Date 2020/7/2
     *@Time 8:50
     */
    @Override
    public Result registerUser(User user, String verifyCode, String captchaCode,String captchaKey, HttpServletRequest request) {
        //检测是够已经注册过
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",user.getUserName());
        User one = baseMapper.selectOne(userQueryWrapper);
        if(one!=null){
            //已经注册过了
            return Result.fail().message("该用户已经注册过");
        }
        //检测邮箱已经注册过
        userQueryWrapper.eq("email",user.getEmail());
        User two = baseMapper.selectOne(userQueryWrapper);
        if(two!=null){
            //已经注册过了
            return Result.fail().message("该邮箱已经注册过");
        }

        //检测邮箱验证码是否正确
            //获取redis中的验证码
        String code = (String) redisUtil.get(Constants.User.KEY_EMAIL_CONTENT + user.getEmail());
        System.out.println(Constants.User.KEY_EMAIL_CONTENT + user.getEmail()+"-------------------------------");
        if(StringUtils.isEmpty(code)){
            return Result.fail().message("邮箱验证码已过期,请稍后尝试");
        }
            //进行比较
        if(!code.equals(verifyCode)){
            return Result.fail().message("用户注册验证码错误");
        }else {
            //删除redis中的验证码
            redisUtil.del(Constants.User.KEY_EMAIL_CONTENT + user.getEmail());
        }
        //检测图灵验证码已经注册过
            //获取redis中的图灵验证码
        String codeTwo = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT+captchaKey);
        if(StringUtils.isEmpty(codeTwo)){
            return Result.fail().message("人机验证码已过期,请稍后尝试");
        }
            //比较
        if(!captchaCode.equals(codeTwo)){
            return Result.fail().message("图灵验证码错误");
        }else {
            //删除redis中的验证码
            redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT+captchaKey);
        }
        //密码加密
        String pwd = bCryptPasswordEncoder.encode(user.getPassword());
        //补全用户信息
        user.setPassword(pwd);
        //设置身份
        user.setRoles(Constants.User.ROLE_ORDINARY);
        //设置头像
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        //设置初始化状态
        user.setState(Constants.User.DEFAULT_STATE);
        //设置注册IP地址
        user.setRegIp(request.getRemoteAddr());
        user.setLoginIp(request.getRemoteAddr());
        //进行注册
        int insert = baseMapper.insert(user);
        if(insert > 0){
            //更新标记
            user.setSign("1");
            return Result.ok().message("用户注册成功");
        }else {
            return Result.ok().message("用户注册失败");
        }
    }
}
