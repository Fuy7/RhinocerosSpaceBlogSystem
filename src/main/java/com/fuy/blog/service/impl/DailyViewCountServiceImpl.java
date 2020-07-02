package com.fuy.blog.service.impl;

import com.fuy.blog.entity.DailyViewCount;
import com.fuy.blog.mapper.DailyViewCountMapper;
import com.fuy.blog.service.DailyViewCountService;
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
public class DailyViewCountServiceImpl extends ServiceImpl<DailyViewCountMapper, DailyViewCount> implements DailyViewCountService {

}
