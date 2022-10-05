package com.ikun.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ikun.base.CommonService;
import com.ikun.modules.system.domain.UsersRoles;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface UsersRolesService extends CommonService<UsersRoles> {
    List<Long> queryUserIdByRoleId(Long id);
    List<Long> queryRoleIdByUserId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByUserId(Long id);


}
