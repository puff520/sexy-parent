package com.ikun.mapper;

import com.ikun.base.CommonMapper;
import com.ikun.domain.QiniuContent;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QiniuContentMapper extends CommonMapper<QiniuContent> {

    QiniuContent findByKey(String key);
}
