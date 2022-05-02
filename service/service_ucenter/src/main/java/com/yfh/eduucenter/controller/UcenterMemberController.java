package com.yfh.eduucenter.controller;


import com.yfh.commonutils.JwtUtils;
import com.yfh.commonutils.R;
import com.yfh.eduucenter.entity.UcenterMember;
import com.yfh.eduucenter.entity.vo.LoginVo;
import com.yfh.eduucenter.entity.vo.RegisterVo;
import com.yfh.eduucenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/eduucenter/ucentermember")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        // 登录成功 返回token值
        String token = ucenterMemberService.login(loginVo);

        return R.ok().data("token", token);
    }

    /**
     * 注册功能实现
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {

        boolean isSuccess = ucenterMemberService.register(registerVo);

        return R.ok();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")

    public R getUserInfo(HttpServletRequest request) {

        // 1. 调用jwt工具类 根据request对象 获取header头信息 返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        // 2. 根据id获取用户信息
        UcenterMember member = ucenterMemberService.getById(id);

        return R.ok().data("member", member);
    }
}

