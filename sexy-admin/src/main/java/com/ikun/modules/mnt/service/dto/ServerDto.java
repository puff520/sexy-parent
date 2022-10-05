package com.ikun.modules.mnt.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ikun.base.CommonDto;

import java.io.Serializable;
import java.util.Objects;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
@NoArgsConstructor
public class ServerDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String account;

    private String ip;

    private String name;

    private String password;

    private Integer port;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerDto that = (ServerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
