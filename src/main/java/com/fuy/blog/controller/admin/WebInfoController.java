package com.fuy.blog.controller.admin;


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
@RequestMapping("/admin/settings")
public class WebInfoController {

    @GetMapping("title")
    public Result getWebtitle(){

        return null;
    }

    @PutMapping("title")
    public Result updateWebtitle(){

        return null;
    }

    @GetMapping("seo")
    public Result getWebSEO(){

        return null;
    }

    @PutMapping("seo")
    public Result updateWebSEO(@RequestParam("keyword") String keyword,
                               @RequestParam("description") String description){

        return null;
    }

    @GetMapping("viewCount")
    public Result getWebViewCount(){

        return null;
    }


}

