package com.ikun.service.dto;

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
public class QiniuContentQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
