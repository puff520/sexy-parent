
package com.ikun.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-6-10 16:32:18
*/
@Data
@NoArgsConstructor
public class DeptSmallDto implements Serializable {

    private Long id;
    private String name;

    public DeptSmallDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
