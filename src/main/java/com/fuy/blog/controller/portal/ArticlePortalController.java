package com.fuy.blog.controller.portal;


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
@RequestMapping("/portal/article")
public class ArticlePortalController {


    @GetMapping("/list/{current}/{limit}")
    public Result getAllArticle(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Article> articlePage = new Page<>(current,limit);
        return null;
    }

    @GetMapping("/list/{categoryID}/{current}/{limit}")
    public Result getArticlesByCategoryID(@PathVariable("categoryID") String categoryID,@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<Article> articlePage = new Page<>(current,limit);
        return null;
    }

    @GetMapping("/{articleID}")
    public Result getArticleInfo(@PathVariable("articleID") String articleID){

        return null;
    }

    @GetMapping("/recommend/{articleID}")
    public Result getRecommendArticle(@PathVariable("articleID") String articleID){

        return null;
    }

}

