package com.ikun.modules.quartz.service.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.modules.quartz.domain.QuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface QuartzJobMapper extends CommonMapper<QuartzJob> {

    List<QuartzJob> findByIsPauseIsFalse();
}
