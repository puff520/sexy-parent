package com.ikun.service.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface LogMapper extends CommonMapper<Log> {

}
