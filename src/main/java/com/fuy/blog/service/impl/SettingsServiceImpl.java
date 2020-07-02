package com.fuy.blog.service.impl;

import com.fuy.blog.entity.Setting;
import com.fuy.blog.mapper.SettingsMapper;
import com.fuy.blog.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
