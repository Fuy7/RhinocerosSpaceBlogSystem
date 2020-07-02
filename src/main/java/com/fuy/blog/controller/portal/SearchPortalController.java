package com.fuy.blog.controller.portal;

import com.fuy.blog.common.result.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuy
 * @create 2020-06-27 18:25
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalController {

    @GetMapping("search")
    public Result doSearch(@RequestParam("keyWord") String keyWord,
                           @RequestParam("current")long current,
                           @RequestParam("limit") long limit){

        return null;
    }



}
