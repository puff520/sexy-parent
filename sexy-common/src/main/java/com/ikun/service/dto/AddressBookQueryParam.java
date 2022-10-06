package com.ikun.service.dto;

import com.ikun.annotation.Query;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Data
// @DataPermission(fieldName = "dept_id")
public class AddressBookQueryParam {

    /** 精确 */
    @Query
    private Long userId;

}
