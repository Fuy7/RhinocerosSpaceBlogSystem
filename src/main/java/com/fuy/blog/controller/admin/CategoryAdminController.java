package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Category;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 *
 *  前端控制器
 * @author fuy
 * @since 2020-06-26
 */
@Api(description="分类管理")
@RestController
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    @PostMapping
    public Result addCategory(@RequestBody Category category){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable String id){

        return null;
    }

    @PutMapping("/{id}")
    public Result updateCategory(@RequestBody Category category){

        return null;
    }

    @GetMapping("/{id}")
    public Result getCategory(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllCategory(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Category> categoryPage = new Page<>(current,limit);
        return null;
    }
}

