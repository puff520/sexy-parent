package com.ikun.service.addressbook;

import com.ikun.base.CommonService;
import com.ikun.base.PageInfo;
import com.ikun.service.domain.User;
import com.ikun.service.dto.AddressBookDto;
import com.ikun.service.dto.AddressBookQueryParam;
import com.ikun.service.dto.AddressBooksDto;
import com.ikun.service.dto.UserDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface AddressBookService extends CommonService<User>{

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<UserDto>
    */
    PageInfo<AddressBookDto> queryAll(AddressBookQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<UserDto>
    */
    List<AddressBookDto> queryAll(AddressBookQueryParam query);


    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<AddressBookDto> all, HttpServletResponse response) throws IOException;

    /**
     * 插入一条新数据。
     */
    boolean save(AddressBooksDto<AddressBookDto> list);

    UserDto saveUser(UserDto resources);
}
