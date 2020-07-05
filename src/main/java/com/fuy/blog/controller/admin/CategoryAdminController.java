package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Category;
import com.fuy.blog.service.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private CategoriesService categoriesService;

    @PreAuthorize("@permission.adminPermission()")
    @ApiOperation(value = "添加分类(需要管理员权限)")
    @PostMapping
    public Result addCategory(@RequestBody Category category){
        return categoriesService.addCategory(category);
    }

    @ApiOperation(value = "删除分类(需要管理员权限)")
    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable String id){

        return null;
    }

    @ApiOperation(value = "修改分类(需要管理员权限)")
    @PutMapping("/{id}")
    public Result updateCategory(@RequestBody Category category){

        return null;
    }

    @ApiOperation(value = "获取分类")
    @GetMapping("/{id}")
    public Result getCategory(@PathVariable String id){

        return null;
    }

    @ApiOperation(value = "获取分类列表")
    @GetMapping("/list/{current}/{limit}")
    public Result getAllCategory(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Category> categoryPage = new Page<>(current,limit);
        return null;
    }
}

