package com.yfh.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.educms.entity.CrmBanner;
import com.yfh.educms.mapper.CrmBannerMapper;
import com.yfh.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-01
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 查询所有banner 降序排序 取前2个

        // last 方法 拼接sql语句 但是最好只拼一个 不然会有注入风险
        List<CrmBanner> bannerList = baseMapper.selectList(new QueryWrapper<CrmBanner>().orderByDesc("id").last("limit 2"));
        return bannerList;

    }
}
