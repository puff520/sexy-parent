package com.ikun.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.result.Result;
import com.ikun.service.domain.MemBaseInfo;
import com.ikun.service.dto.LoginDto;
import com.ikun.service.mapper.MemBaseInfoMapper;
import com.ikun.service.mem.MemBaseInfoService;
import com.ikun.utils.CasinoWebUtil;
import com.ikun.constant.Constants;
import com.ikun.utils.JjwtUtil;
import com.ikun.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jinjin
 * @date 2020-09-25
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemBaseInfoServiceImpl extends CommonServiceImpl<MemBaseInfoMapper, MemBaseInfo> implements MemBaseInfoService {

    @Autowired
    RedisUtils redisUtil;

    @Override
    public Result login(LoginDto dto) {
        MemBaseInfo user = this.getMemBaseInfoByUserName(dto.getUserName());
        if (user == null) {
            return Result.failed("帐号或密码错误");
        }
        String bcryptPassword = user.getPassword();
        boolean bcrypt = CasinoWebUtil.checkBcrypt(dto.getPassword(), bcryptPassword);
        if (!bcrypt) {
            return Result.failed("帐号或密码错误");
        }
        JjwtUtil.Subject subject = new JjwtUtil.Subject();
        subject.setUserId(String.valueOf(user.getMemId()));
        subject.setBcryptPassword(user.getPassword());
        String token = JjwtUtil.generic(subject, Constants.CASINO_WEB);
        setUserTokenToRedis(user.getMemId(), token);
        return Result.success(token);
    }

    @Override
    public MemBaseInfo getMemBaseInfoById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public MemBaseInfo getMemBaseInfoByUserName(String userName) {
        LambdaQueryWrapper<MemBaseInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemBaseInfo::getUsername, userName);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable(cacheNames = "mem" ,key = "#p0",unless = "#result==null")
    public MemBaseInfo memInfo(String ycp) {
        return getMemBaseInfoById(1L);
    }

    private void setUserTokenToRedis(Long userId, String token) {
        JjwtUtil.Token jwtToken = new JjwtUtil.Token();
        jwtToken.setOldToken(token);
        redisUtil.set(Constants.TOKEN_CASINO_WEB + userId, jwtToken, JjwtUtil.ttl + Constants.WEB_REFRESH_TTL);
    }
}
