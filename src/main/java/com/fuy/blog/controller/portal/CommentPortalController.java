package com.fuy.blog.controller.portal;


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
@RequestMapping("/portal/comment")
public class CommentPortalController {


    @PostMapping
    public Result addComment(@RequestBody Comment comment){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable String id){

        return null;
    }

    @PutMapping("/list/{articleID}/{current}/{limit}")
    public Result listComment(@PathVariable String articleID,
                              @PathVariable("current")long current,
                              @PathVariable("limit") long limit){

        return null;
    }

}


