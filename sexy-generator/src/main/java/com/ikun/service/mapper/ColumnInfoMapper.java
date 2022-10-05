package com.ikun.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ikun.base.CommonMapper;
import com.ikun.domain.ColumnInfo;
import com.ikun.domain.vo.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
@Mapper
public interface ColumnInfoMapper extends CommonMapper<ColumnInfo> {

    List<TableInfo> getTables();

    int getTablesTotal();

    IPage<TableInfo> selectPageOfTables(IPage<?> page, @Param("name") String tableName);

    List<ColumnInfo> queryColumnInfo(String tableName);
}
