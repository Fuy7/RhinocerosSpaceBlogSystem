package com.fuy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Article;
import com.fuy.blog.entity.Category;
import com.fuy.blog.entity.User;
import com.fuy.blog.mapper.ArticleMapper;
import com.fuy.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuy.blog.service.CategoriesService;
import com.fuy.blog.service.UserService;
import com.fuy.blog.utils.Constants;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuy
 * @since 2020-06-26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoriesService categoriesService;

    /**
    *@Description 添加文章
    *@Author fuy
    *@Date 2020/7/12
    *@Time 17:25
    */
    @Override
    public Result addArticle(Article article) {

        //校验分类ID
        Result result = categoriesService.getCategory(article.getCategoryId());
        Map<String, Object> data = result.getData();
        Category category = (Category)data.get("category");
        if(category == null){
            return Result.fail().message("分类不存在");
        }
        //校验题目长度
        if(article.getTitle().length() > Constants.Article.TITE_MAX_LENGTH){
            return Result.fail().message("标题长度超出限制");
        }

        //获取 request response
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        User user = userService.checkUser(request, response);

        //补全信息
        article.setViewCount(Constants.Article.VIEW_COUNT);
        //发布状态
        article.setState(Constants.Article.PUBLISHED_STATUS);
        article.setUserId(user.getId());
//        article.setCategoryId()

        //保存
        int insert = baseMapper.insert(article);
        if(insert <= 0){
            return Result.fail().message("添加文章失败");
        }
        //修改文章状态
        article.setState("1");
        baseMapper.updateById(article);
        return Result.ok().message("添加文章成功");
    }

    /**
    *@Description 删除文章
    *@Author fuy
    *@Date 2020/7/12
    *@Time 19:24
    */
    @Override
    public Result deleteArticle(String id) {

        Article article = baseMapper.selectById(id);
        article.setState(Constants.Article.DELETE_STATUS);
        int i = baseMapper.updateById(article);
        if(i <= 0){
            return Result.fail().message("文章删除失败");
        }
        return Result.ok().message("文章删除成功");
    }

    /**
    *@Description 分页查询文章
    *@Author fuy
    *@Date 2020/7/12
    *@Time 19:26
    */
    @Override
    public Result getAllArticle(long current, long limit, String state, String keyWord, String categoryId) {

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }
        if(!StringUtils.isEmpty(categoryId)){
            wrapper.eq("category_id",categoryId);
        }
        if(!StringUtils.isEmpty(keyWord)){
            wrapper.like("title",keyWord);
        }
        wrapper.orderByDesc("state","update_time");
        wrapper.select("id","title","cover","user_id","category_id",
                "type","state","summary","labels","view_count","create_time","update_time");
        Page<Article> page = new Page<>(current,limit);
        baseMapper.selectMapsPage(page,wrapper);
        return Result.ok().data("page",page);
    }

    /**
    *@Description 获取文章详情
    *@Author fuy
    *@Date 2020/7/12
    *@Time 20:06
    */
    @Override
    public Result getArticle(String id) {

        Article article = baseMapper.selectById(id);
        if(article == null){
            return Result.fail().message("该文章不存在");
        }

        String state = article.getState();
        //获取文章作者的信息
        String userId = article.getUserId();
        User u = userService.getById(userId);
        String userName = u.getUserName();
        String avatar = u.getAvatar();
        String sign = u.getSign();
        Map<String,String>  userMap = new HashMap<>();
        System.out.println("-----------------------" + u + "-----------------------" );
        userMap.put("userName",userName);
        userMap.put("avatar",avatar);
        userMap.put("sign",sign);
        //分割标签
        String labels = article.getLabels();
        String[] split = labels.split("、");
        List<String> list = new ArrayList<>();
        BeanUtils.copyProperties(split,list);
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        if(state.equals(Constants.Article.PUBLISHED_STATUS)
                || state.equals(Constants.Article.TOP_STATUS)){

            return Result.ok().data("article",article).data("user",userMap).data("labels",list);

        }
        //判断是不是管理员
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        User user = userService.checkUser(request, response);
        if(!user.getRoles().equals(Constants.User.ROLE_ADMIN) && !user.getRoles().equals(Constants.User.ROLE_ORDINARY)){
            return Result.insufficientPermissions().message("访问权限不足");
        }


        return Result.ok().data("article",article).data("user",userMap).data("labels",list);
    }

    /**
    *@Description 更新文章
    *@Author fuy
    *@Date 2020/7/12
    *@Time 21:01
    */
    @Override
    public Result updateArticle(String id, Article article) {
        Article a = baseMapper.selectById(id);
        if(a == null){
            return Result.fail().message("文章不存在");
        }
        if(StringUtils.isEmpty(article.getTitle())){
            return Result.fail().message("文章标题不允许为空");
        }
        article.setId(id);
        int i = baseMapper.updateById(article);
        if(i <= 0){
            return Result.fail().message("文章修改失败");
        }
        return Result.ok();
    }

    /**
    *@Description 顶置文章
    *@Author fuy
    *@Date 2020/7/12
    *@Time 21:13
    */
    @Override
    public Result topArticle(String id) {

        //只有发布的文章才能顶置
        Article article = baseMapper.selectById(id);
        if(article == null){
            return Result.fail().message("该文章不存在");
        }
        if(article.getState().equals(Constants.Article.PUBLISHED_STATUS)){
            //置顶
            article.setState(Constants.Article.TOP_STATUS);
            int i = baseMapper.updateById(article);
            if(i <= 0){
                return Result.fail().message("文章顶置失败");
            }
            return Result.ok().message("文章顶置成功");
        }
        if(article.getState().equals(Constants.Article.TOP_STATUS)){
            //置顶过的取消顶置
            article.setState(Constants.Article.PUBLISHED_STATUS);
            int i = baseMapper.updateById(article);
            if(i <= 0){
                return Result.fail().message("文章顶置取消失败");
            }
            return Result.ok().message("取消文章顶置");
        }

        return Result.fail().message("只有发布的文章才能顶置或取消顶置");
    }
}
