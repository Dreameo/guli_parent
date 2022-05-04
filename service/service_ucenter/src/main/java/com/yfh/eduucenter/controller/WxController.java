package com.yfh.eduucenter.controller;


import com.google.gson.Gson;
import com.yfh.commonutils.HttpClientUtils;
import com.yfh.commonutils.JwtUtils;
import com.yfh.eduucenter.entity.UcenterMember;
import com.yfh.eduucenter.service.UcenterMemberService;
import com.yfh.eduucenter.utils.ConstantPropertiesUtil;
import com.yfh.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    // 1. 生成微信扫描二维码
    @GetMapping("/login")
    public String login() {
        // 固定地址,后面拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + ConstantPropertiesUtil.WX_OPEN_APP_ID
//                + "";  // 第一种方式

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址 对Redirect_url进行编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }


        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);



        // 重定向 请求微信地址
//        return "redirect:http://localhost:3000";
        return "redirect:" + qrcodeUrl;
    }



    // 2. 获取扫描人的信息， 添加数据
    @GetMapping("/callback") // callback有两个参数 code 和 state
    public String callback(String code, String state) {

        try {
            // 1. 先获取code值， 临时票据， 类似验证码

            // 2. 拿着code 请求， 微信固定地址， 得到两个值 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            // 拼接参数
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);

            // 有地址之后  请求拼接好的这个地址， 返回两个access_token 和 openid
            // 使用 httpclient发送请求 得到结果， 是一种比较古老的技术， 能够不借用浏览器 得到请求响应

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo = " + accessTokenInfo);

            // json字符串形式 就是 key value结构
            // 方法一 ：字符串分割
            // 方法二 ： 转化为map形式


            // 将 accessTokenInfo转化为map形式 用gson
            Gson gson = new Gson();
            HashMap mapAccessTokenInfo = gson.fromJson(accessTokenInfo, HashMap.class);

            String access_token = (String) mapAccessTokenInfo.get("access_token");
            String openid = (String) mapAccessTokenInfo.get("openid");



            // 扫码信息如果数据库有 就不加入， 没有就进行加入
            // 根据openid 查看数据库是否有这个用户
            UcenterMember ucenterMember = ucenterMemberService.getOpenIdMember(openid);

            if(ucenterMember == null) {
                // 3. 将获取到的access_token 和openid 再去请求固定地址  获取扫描人的信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

                // 发送请求：
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo = " + userInfo);

                // 获取返回userinfo字符串 扫描人信息
                HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
                String headimgurl = (String) mapUserInfo.get("headimgurl");

                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setAvatar(headimgurl);
                ucenterMember.setNickname(nickname);
                ucenterMemberService.save(ucenterMember);
            }
            // 利用jwt生成 token
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

            // 返回首页
            return "redirect:http://localhost:3000?token=" + jwtToken;
            // 如果不为空
        } catch (Exception e) {
            throw new GuliException(20001, "登录失败");
        }


//        return "redirect:http://localhost:3000";
    }
}
