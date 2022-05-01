package com.yfh.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.commonutils.R;
import com.yfh.educms.entity.CrmBanner;
import com.yfh.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-01
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 1. 分页查询banner
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);

        bannerService.page(pageBanner, null);

        List<CrmBanner> records = pageBanner.getRecords();
        long total = pageBanner.getTotal();

        return R.ok().data("rows", records).data("total", total) ;
    }

    /**
     * 2. 添加banner
     */
    @PostMapping("/addBanner")
    public R addBeanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 3. 修改banner
     */
    @PutMapping("/update")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 4. 删除banner
     */
    @DeleteMapping("/deleteBannerById/{id}")
    public R deleteBannerById(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    /**
     * 4. 根据id查询banner
     */
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id) {
        bannerService.getById(id);
        return R.ok();
    }
}

