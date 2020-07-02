package com.fuy.blog.service.impl;

import com.fuy.blog.entity.Category;
import com.fuy.blog.mapper.CategoriesMapper;
import com.fuy.blog.service.CategoriesService;
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
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Category> implements CategoriesService {

}
