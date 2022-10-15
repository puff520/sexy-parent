package com.ikun.service.mem;

import com.ikun.base.CommonService;
import com.ikun.pojo.vo.LoginVo;
import com.ikun.result.Result;
import com.ikun.service.domain.MemBaseInfo;
import com.ikun.service.domain.User;
import com.ikun.service.dto.LoginDto;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface MemBaseInfoService extends CommonService<MemBaseInfo>{




    Result login(LoginDto id);

    MemBaseInfo getMemBaseInfoById(Long id);

    MemBaseInfo getMemBaseInfoByUserName(String userName);

    MemBaseInfo memInfo();


}
