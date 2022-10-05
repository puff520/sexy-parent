package com.ikun.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ikun.base.CommonService;
import com.ikun.modules.system.domain.RolesDepts;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface RolesDeptsService extends CommonService<RolesDepts> {

    List<Long> queryDeptIdByRoleId(Long id);
    List<Long> queryRoleIdByDeptId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByDeptId(Long id);
}
