package com.ikun.modules.system.service.dto;

import lombok.*;
import com.ikun.base.CommonDto;

import java.io.Serializable;
import java.util.Objects;

/**
* @author jinjin
* @date 2020-09-25
*/
@Data
@NoArgsConstructor
public class JobDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Boolean enabled;

    private Integer jobSort;

    public JobDto(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobDto dto = (JobDto) o;
        return Objects.equals(id, dto.id) &&
                Objects.equals(name, dto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
