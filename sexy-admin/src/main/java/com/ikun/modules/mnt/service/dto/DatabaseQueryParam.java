package com.ikun.modules.mnt.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import com.ikun.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
public class DatabaseQueryParam{

    /** 模糊 */
    @Query(blurry = "name,userName,jdbcUrl")
    private String blurry;

    /** 精确 */
    @Query
    private String jdbcUrl;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
