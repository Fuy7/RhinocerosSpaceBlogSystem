package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Comment;
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
@RequestMapping("/admin/comment")
public class CommentController {



    @GetMapping("/{id}")
    public Result getComment(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllComment(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Comment> commentPage = new Page<>(current,limit);
        return null;
    }

    @PutMapping("/top/{id}")
    public Result topComment(@PathVariable String id) {

        return null;
    }
}

