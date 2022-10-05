package com.ikun.modules.mnt.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.modules.mnt.domain.App;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface AppMapper extends CommonMapper<App> {

}
