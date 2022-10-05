package com.ikun.modules.mnt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ikun.base.CommonService;
import com.ikun.modules.mnt.domain.DeploysServers;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface DeploysServersService extends CommonService<DeploysServers> {
    List<Long> queryDeployIdByServerId(Long id);
    List<Long> queryServerIdByDeployId(Long id);
    boolean removeByDeployId(Long id);
    boolean removeByServerId(Long id);
}
