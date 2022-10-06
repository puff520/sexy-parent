package com.ikun.service.addressbook.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ikun.base.PageInfo;
import com.ikun.base.QueryHelpMybatisPlus;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.service.addressbook.AddressBookService;
import com.ikun.service.domain.AddressBook;
import com.ikun.service.domain.User;
import com.ikun.service.dto.AddressBookDto;
import com.ikun.service.dto.AddressBookQueryParam;
import com.ikun.service.dto.UserDto;
import com.ikun.service.mapper.AddressBookMapper;
import com.ikun.service.mapper.UserMapper;
import com.ikun.utils.ConvertUtil;
import com.ikun.utils.FileUtil;
import com.ikun.utils.PageUtil;
import com.ikun.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
