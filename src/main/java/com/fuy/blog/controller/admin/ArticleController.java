package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Article;
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
@RequestMapping("/admin/article")
public class ArticleController {

    @PostMapping
    public Result addArticle(@RequestBody Article article){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable String id){

        return null;
    }

    @PutMapping("/{id}")
    public Result updateArticle(@PathVariable String id){

        return null;
    }

    @GetMapping("/{id}")
    public Result getArticle(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllArticle(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Article> articlePage = new Page<>(current,limit);
        return null;
    }

    @PutMapping("/state/{id}/{state}")
    public Result updateArticleState(@PathVariable("state") String state,@PathVariable("id") String id){

        return null;
    }

    @PutMapping("/top/{id}")
    public Result topArticle(@PathVariable String id){

        return null;
    }
}

