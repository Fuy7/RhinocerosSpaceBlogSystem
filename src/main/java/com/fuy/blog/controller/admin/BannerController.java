package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Banner;
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
@RequestMapping("/admin/banner")
public class BannerController {

    @PostMapping
    public Result addBanner(@RequestBody Banner banner){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteBanner(@PathVariable String id){

        return null;
    }

    @PutMapping("/{id}")
    public Result updateBanner(@RequestBody Banner banner){

        return null;
    }

    @GetMapping("/{id}")
    public Result getBanner(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllBanner(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Banner> bannerPage = new Page<>(current,limit);
        return null;
    }
}

