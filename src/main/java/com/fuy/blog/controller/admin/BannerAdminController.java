package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Banner;
import com.fuy.blog.service.BannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
@Api(description="轮播图管理")
@RestController
@RequestMapping("/admin/banner")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    @PreAuthorize("@permission.adminPermission()")
    @PostMapping
    public Result addBanner(@RequestBody @Validated Banner banner){

        return bannerService.addBanner(banner);
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
        Banner banner = bannerService.getById(id);
        return Result.ok().data("banner",banner) ;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllBanner(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Banner> bannerPage = new Page<>(current,limit);
        return null;
    }
}

