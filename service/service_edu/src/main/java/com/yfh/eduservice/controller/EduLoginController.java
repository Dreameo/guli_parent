package com.yfh.eduservice.controller;

import com.yfh.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@Slf4j
@CrossOrigin // 解决跨域问题
public class EduLoginController {

    // login

    @PostMapping("/login")
    public R login() {
        log.info("进入login");
        return R.ok().data("token", "admin");
    }


    // info

    @GetMapping("/info")
    public R info() {
        log.info("进入info");
        return R.ok().data("name","[admin]").data("roles", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
