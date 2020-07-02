package com.fuy.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.FriendLink;
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
@RequestMapping("/admin/friends")
public class FriendsController {


    @PostMapping
    public Result addFriends(@RequestBody FriendLink friendLink){

        return null;
    }

    @DeleteMapping("/{id}")
    public Result deleteFriends (@PathVariable String id){

        return null;
    }

    @PutMapping("/{id}")
    public Result updateFriends(@PathVariable String id){

        return null;
    }

    @GetMapping("/{id}")
    public Result getFriends(@PathVariable String id){

        return null;
    }

    @GetMapping("/list/{current}/{limit}")
    public Result getAllFriends(@PathVariable("current")long current,@PathVariable("limit") long limit){
        Page<FriendLink> friendsPage = new Page<>(current,limit);
        return null;
    }
}

