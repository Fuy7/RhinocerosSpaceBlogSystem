package com.fuy.blog.controller.portal;


import com.fuy.blog.common.result.Result;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/portal/settings")
public class WebInfoPortalController {

    @GetMapping("list")
    public Result getCategories(){

        return null;
    }

    @GetMapping("title")
    public Result getWebtitle(){

        return null;
    }


    @GetMapping("viewCount")
    public Result getWebViewCount(){

        return null;
    }


    @GetMapping("banner")
    public Result getbanner(){

        return null;
    }

    @GetMapping("friendLink")
    public Result friendLinks(){

        return null;
    }
}

