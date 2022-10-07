
package com.ikun.modules.controller;

import com.ikun.annotation.Log;
import com.ikun.service.addressbook.AddressBookService;
import com.ikun.service.dto.AddressBookDto;
import com.ikun.service.dto.AddressBookQueryParam;
import com.ikun.service.dto.AddressBooksDto;
import com.ikun.service.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Api(tags = "通讯录")
@RestController
@RequestMapping("/app/addressBook")
@RequiredArgsConstructor
public class AddressBookController {

    @Resource
    private final AddressBookService addressBookService;

//    @ApiOperation("导出通讯录数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('user:list')")
//    public void download(HttpServletResponse response, AddressBookQueryParam criteria) throws IOException {
//        addressBookService.download(addressBookService.queryAll(criteria), response);
//    }
//
    @ApiOperation("查询通讯录数据")
    @GetMapping
    public ResponseEntity<Object> query(AddressBookQueryParam criteria, Pageable pageable){
//        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
//            criteria.getDeptIds().add(criteria.getDeptId());
//            criteria.getDeptIds().addAll(deptService.getDeptChildren(criteria.getDeptId(),
//                    deptService.findByPid(criteria.getDeptId())));
//        }
//        // 数据权限
//        List<Long> dataScopes = dataService.getDeptIds(userService.findByName(SecurityUtils.getCurrentUsername()));
//        // criteria.getDeptIds() 不为空并且数据权限不为空则取交集
//        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)){
//            // 取交集
//            criteria.getDeptIds().retainAll(dataScopes);
//            if(!CollectionUtil.isEmpty(criteria.getDeptIds())){
//                return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
//            }
//        } else {
//            // 否则取并集
//            criteria.getDeptIds().addAll(dataScopes);
//            return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(PageUtil.toPage(null,0),HttpStatus.OK);
        return new ResponseEntity<>(addressBookService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    @Log("新增通讯录数据")
    @ApiOperation("新增通讯录数据")
    @PostMapping
    public ResponseEntity<Object> create(@Validated @RequestBody AddressBooksDto<AddressBookDto> resources){
        addressBookService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @Log("删除通讯录数据")
//    @ApiOperation("删除通讯录数据")
//    @DeleteMapping
//    @PreAuthorize("@el.check('user:del')")
//    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
//        addressBookService.removeByIds(ids);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody UserDto resources){
        return new ResponseEntity<>(addressBookService.saveUser(resources),HttpStatus.CREATED);
    }
}
