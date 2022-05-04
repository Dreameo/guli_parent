package com.yfh.eduucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.commonutils.JwtUtils;
import com.yfh.eduucenter.entity.UcenterMember;
import com.yfh.eduucenter.entity.vo.LoginVo;
import com.yfh.eduucenter.entity.vo.RegisterVo;
import com.yfh.eduucenter.mapper.UcenterMemberMapper;
import com.yfh.eduucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfh.eduucenter.utils.MD5;
import com.yfh.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 1. 手机号或者密码为空 返回提示错误信息
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "手机或者密码输入为空!");
        }

        // 2. 根据前端传来的手机号码查找 信息
        UcenterMember ucenterMember = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile)); // 根据手机号码查找的用户信息

        // 3. 判断是否为空 如果为空表示没查找到信息， 因此返回错误信息
        if(ucenterMember == null) {
            throw new GuliException(20001, "手机号码或密码错误");
        }

        // 4. 检查密码是否与输入密码一致
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001, "手机号码或密码错误");
        }

        // 5. 判断用户是否被禁用
        if(ucenterMember.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用！");
        }

        // 都正确 生成token
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        //1. 获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //2. 校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"参数为空");
        }

        // 3. 判断code是否正确
        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = (String) redisTemplate.opsForValue().get("msm::" +mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"error");
        }


        //4. 查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"error");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);



        return false;
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        UcenterMember ucenterMember = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("openid", openid));
        return ucenterMember;
    }
}
