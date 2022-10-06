package com.ikun.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ikun.base.PageInfo;
import com.ikun.base.QueryHelpMybatisPlus;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.config.FileProperties;
import com.ikun.exception.BadRequestException;
import com.ikun.exception.EntityExistException;
import com.ikun.modules.security.service.OnlineUserService;
import com.ikun.modules.security.service.UserCacheClean;
import com.ikun.modules.system.domain.AddressBook;
import com.ikun.modules.system.domain.User;
import com.ikun.modules.system.domain.UsersJobs;
import com.ikun.modules.system.domain.UsersRoles;
import com.ikun.modules.system.service.*;
import com.ikun.modules.system.service.dto.*;
import com.ikun.modules.system.service.mapper.AddressBookMapper;
import com.ikun.modules.system.service.mapper.UserMapper;
import com.ikun.modules.system.service.mapper.UsersJobsMapper;
import com.ikun.modules.system.service.mapper.UsersRolesMapper;
import com.ikun.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-09-25
*/
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "addressBook")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AddressBookServiceImpl extends CommonServiceImpl<UserMapper, User> implements AddressBookService {

    @Resource
    private final RedisUtils redisUtils;
    @Resource
    private final AddressBookMapper addressBookMapper;
    @Resource
    private final UserMapper userMapper;

    @Override
    //@Cacheable
    public PageInfo<AddressBookDto> queryAll(AddressBookQueryParam query, Pageable pageable) {
        IPage<AddressBook> page = PageUtil.toMybatisPage(pageable);
        IPage<AddressBook> pageData = addressBookMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        List<AddressBookDto> addressBookDtos = ConvertUtil.convertList(pageData.getRecords(), AddressBookDto.class);
        return new PageInfo<>(pageData.getTotal(), addressBookDtos);
    }

    @Override
    //@Cacheable
    public List<AddressBookDto> queryAll(AddressBookQueryParam query){
        return ConvertUtil.convertList(addressBookMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), AddressBookDto.class);
    }

    @Override
    public void download(List<AddressBookDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (AddressBookDto addressBookDto : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("用户ID", addressBookDto.getId());
              map.put("通讯录姓名", addressBookDto.getUserName());
              map.put("手机号码", addressBookDto.getPhone());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(AddressBookDto resources) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserName, resources.getUserName());
        wrapper.eq(AddressBook::getPhone, resources.getPhone());
        AddressBook oldAddressBook = addressBookMapper.selectOne(wrapper);
        if(null==oldAddressBook) {
            AddressBook addressBook = new AddressBook();
            BeanUtils.copyProperties(resources, addressBook);
            addressBookMapper.insert(addressBook);
        }
        return true;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto saveUser(UserDto resources){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, resources.getUsername());
        User user = userMapper.selectOne(wrapper);
        if(null == user){
           int id = userMapper.insert(user);
            resources.setId((long) id);
        }else {
            resources.setId(user.getId());
        }
        return resources;
    }
}
