package com.ikun.service.dto;

import com.ikun.base.CommonDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddressBooksDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    private List<T> key;

}
