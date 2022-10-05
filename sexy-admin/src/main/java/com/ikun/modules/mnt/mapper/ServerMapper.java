package com.ikun.modules.mnt.mapper;

import com.ikun.modules.mnt.domain.Server;
import com.ikun.base.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface ServerMapper extends CommonMapper<Server> {
    List<Server> selectAllByDeployId(Long id);
}
