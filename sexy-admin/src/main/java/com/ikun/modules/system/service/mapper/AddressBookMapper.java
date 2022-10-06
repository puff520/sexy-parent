package com.ikun.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ikun.base.CommonMapper;
import com.ikun.modules.system.domain.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Mapper
public interface AddressBookMapper extends CommonMapper<AddressBook> {

}
