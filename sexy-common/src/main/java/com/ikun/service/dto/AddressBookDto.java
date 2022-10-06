package com.ikun.service.dto;

import com.ikun.base.CommonDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressBookDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String phone;

    private Long userId;
}
