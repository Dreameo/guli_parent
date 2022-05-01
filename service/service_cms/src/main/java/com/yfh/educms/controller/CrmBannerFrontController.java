package com.yfh.educms.controller;


import com.yfh.commonutils.R;
import com.yfh.educms.entity.CrmBanner;
import com.yfh.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 1. 查询所有banner
     */
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> bannerList = bannerService.selectAllBanner(); // 为了加redis方便， 加一个方法
        return R.ok().data("bannerList", bannerList);
    }

}

