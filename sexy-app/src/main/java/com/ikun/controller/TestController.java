
package com.ikun.controller;

import com.ikun.service.addressbook.AddressBookService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "通讯录")
@RequestMapping("/app/test")
@RequiredArgsConstructor
public class TestController {

    private final AddressBookService addressBookService;



    @GetMapping(value = "/hello")
    public ResponseEntity<Object> hello(){
        return new ResponseEntity<>("1111",HttpStatus.CREATED);
    }
}
