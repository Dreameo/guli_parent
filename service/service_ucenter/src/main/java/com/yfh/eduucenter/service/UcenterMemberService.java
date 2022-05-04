package com.yfh.eduucenter.service;

import com.yfh.eduucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yfh.eduucenter.entity.vo.LoginVo;
import com.yfh.eduucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    boolean register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
