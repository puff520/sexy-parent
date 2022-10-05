package com.ikun.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ikun.base.CommonMapper;
import com.ikun.modules.system.domain.DictDetail;
import com.ikun.modules.system.service.dto.DictDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-24
*/
@Mapper
public interface DictDetailMapper extends CommonMapper<DictDetail> {

    List<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName);
    IPage<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName, IPage<DictDetailDto> page);
}
