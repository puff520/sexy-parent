package com.ikun.modules.quartz.service.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.modules.quartz.domain.QuartzLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface QuartzLogMapper extends CommonMapper<QuartzLog> {

}
