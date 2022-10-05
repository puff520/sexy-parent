package com.ikun.modules.system.service.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.modules.system.domain.UsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-25
*/
@Mapper
public interface UsersRolesMapper extends CommonMapper<UsersRoles> {

}
