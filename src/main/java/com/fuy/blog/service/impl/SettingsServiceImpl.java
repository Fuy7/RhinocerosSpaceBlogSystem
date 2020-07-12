package com.fuy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuy.blog.common.result.Result;
import com.fuy.blog.entity.Setting;
import com.fuy.blog.mapper.SettingsMapper;
import com.fuy.blog.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuy.blog.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
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
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Setting> implements SettingsService {

    /**
    *@Description 获取网站标题
    *@Author fuy
    *@Date 2020/7/12
    *@Time 15:53
    */
    @Override
    public Result getWebtitle() {
        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.eq("s_key", Constants.Setting.WEB_TITE);
        Setting title = baseMapper.selectOne(wrapper);
        return Result.ok().message("获取网站标题成功").data("title",title);
    }

    /**
    *@Description 设置网站标题
    *@Author fuy
    *@Date 2020/7/12
    *@Time 16:02
    */
    @Override
    public Result updateWebtitle(String title) {
        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.eq("s_key", Constants.Setting.WEB_TITE);
        Setting setting = baseMapper.selectOne(wrapper);
        if(setting == null){
            //添加操作
            Setting s = new Setting();
            s.setSKey(Constants.Setting.WEB_TITE);
            s.setSValue(title);
            int i = baseMapper.insert(s);
            if(i <= 0){
                return Result.fail().message("添加标题失败");
            }
            return Result.ok().message("添加标题成功");
        }
        setting.setSValue(title);
        int i = baseMapper.updateById(setting);
        if(i <= 0){
            return Result.fail().message("修改标题失败");
        }
        return Result.ok().message("修改标题成功");
    }

    /**
    *@Description 获取SEO信息
     * SEO（Search Engine Optimization）：汉译为搜索引擎优化。
     * 是一种方式：利用搜索引擎的规则提高网站在有关搜索引擎内的自然排名。
     * 目的是让其在行业内占据领先地位，获得品牌收益。
     * 很大程度上是网站经营者的一种商业行为，将自己或自己公司的排名前移。
    *@Author fuy
    *@Date 2020/7/12
    *@Time 16:09
    */
    @Override
    public Result getWebSEO() {

        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.eq("s_key", Constants.Setting.WEB_DESCRIPTION);
        Setting description = baseMapper.selectOne(wrapper);

        QueryWrapper<Setting> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("s_key", Constants.Setting.WEB_KEYWORD);
        Setting keyword = baseMapper.selectOne(wrapper2);

        Map<String,String> result = new HashMap<>();
        result.put(description.getSKey(),description.getSValue());
        result.put(keyword.getSKey(),keyword.getSValue());
        return Result.ok().message("获取网站SEO信息成功").data("result",result);

    }

    /**
    *@Description 修改网站的SEO
    *@Author fuy
    *@Date 2020/7/12
    *@Time 16:17
    */
    @Override
    public Result updateWebSEO(String keyword, String description) {

        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.eq("s_key", Constants.Setting.WEB_KEYWORD);
        Setting key = baseMapper.selectOne(wrapper);
        if(key == null){
            //添加操作
            Setting s = new Setting();
            s.setSKey(Constants.Setting.WEB_KEYWORD);
            s.setSValue(keyword);
            int i = baseMapper.insert(s);
            if(i <= 0){
                return Result.fail().message("添加keyword失败");
            }
        }

        wrapper.eq("s_key", Constants.Setting.WEB_DESCRIPTION);
        Setting des = baseMapper.selectOne(wrapper);
        if(des == null){
            //添加操作
            Setting s = new Setting();
            s.setSKey(Constants.Setting.WEB_DESCRIPTION);
            s.setSValue(description);
            int i = baseMapper.insert(s);
            if (i <= 0) {
                if (i <= 0) {
                    return Result.fail().message("添加description失败");
                }
            }
        }
        return Result.ok().message("更新SEO成功");
    }
}
