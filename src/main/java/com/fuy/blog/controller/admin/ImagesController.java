package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Images;
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
@RequestMapping("/admin/images")
public class ImagesController {


    @PostMapping
    public Result addImages(@RequestBody Images images){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteImages(@PathVariable String id){

        return null;
    }

    @PutMapping("/{id}")
    public Result updateImages(@RequestBody Images images){

        return null;
    }

    @GetMapping("/{id}")
    public Result getImages(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllImages(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Images> imagesPage = new Page<>(current,limit);
        return null;
    }


}

