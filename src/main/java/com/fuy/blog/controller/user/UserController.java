package com.fuy.blog.controller.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.User;
import com.fuy.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
@Api(description="管理员管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @ApiOperation(value = "初始化管理员")
    @PostMapping("/init")
    public Result initAdmin(@Validated @RequestBody  User user, HttpServletRequest request){

        Result result = service.initAdmin(user,request);

        return result;
    }


    @ApiOperation(value = "注册管理员")
    @PostMapping
    public Result registerUser(@Validated @RequestBody User user,
                               @RequestParam("verifyCode") String verifyCode,
                               @RequestParam("captchaCode") String captchaCode,
                               @RequestParam("captchaKey") String captchaKey,
                               HttpServletRequest request){
        return service.registerUser(user,verifyCode,captchaCode,captchaKey,request);

    }


    @ApiOperation(value = "登陆管理员")
    @PutMapping("/{captcha}")
    public Result loginUser(@PathVariable String captcha,@RequestBody User user){

        return Result.ok().message("登陆成功");
    }



    @ApiOperation(value = "获取图灵验证码校验")
    @GetMapping("/captcha")
    public Result getVerifyCode(HttpServletResponse response, @RequestParam("key_captcha") String captchaKey){
        //将生成的验证码存到redis中
        service.getCaptcha(response,captchaKey);
        return Result.ok();
    }


    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendVerifyCode/{email}")
    public Result sendVerifyCode(@PathVariable String email, HttpServletRequest request,@RequestParam("type") String type){

        return service.sendEmail(request,email,type);
    }


    @ApiOperation(value = "修改密码")
    @PutMapping("/password/{userId}")
    public Result updatePwd(@RequestBody User user,@PathVariable String userId){

        return null;
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/{userId}")
    public Result getUserInfo(User user){

        return null;
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/{userId}")
    public Result updateUserInfo(@RequestBody User user){

        return null;
    }

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list/{current}/{limit}")
    public Result getUserList(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<User> userPage = new Page<>(current,limit);
        return null;
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable String userId){

        return null;
    }

}

