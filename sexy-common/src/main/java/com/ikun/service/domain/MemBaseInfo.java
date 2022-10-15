package com.ikun.service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ikun.base.CommonEntity;
import com.ikun.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

/**
* @author jinjin
* @date 2020-09-25
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("mem_baseInfo")
public class MemBaseInfo extends CommonEntity<MemBaseInfo> implements UserDetails {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "mem_id", type= IdType.AUTO)
    private Long memId;

    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号状态")
    private Integer accStatus;



    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
        authorities.add(authority);
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }


    public static boolean checkUser(MemBaseInfo user) {
        if (user == null) {
            return false;
        }

        if (!Constants.open.equals(user.getAccStatus())) {
            return false;
        }
        return true;
    }

}
