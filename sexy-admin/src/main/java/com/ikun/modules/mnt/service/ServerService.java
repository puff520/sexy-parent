package com.ikun.modules.mnt.service;

import com.ikun.modules.mnt.service.dto.ServerDto;
import com.ikun.modules.mnt.service.dto.ServerQueryParam;
import com.ikun.base.CommonService;
import com.ikun.base.PageInfo;
import com.ikun.modules.mnt.domain.Server;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-27
*/
public interface ServerService  extends CommonService<Server>{

    static final String CACHE_KEY = "server";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<ServerDto>
    */
    PageInfo<ServerDto> queryAll(ServerQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<ServerDto>
    */
    List<ServerDto> queryAll(ServerQueryParam query);

    Server getById(Long id);
    ServerDto findById(Long id);
    ServerDto findByIp(String ip);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(Server resources);
    @Override
    boolean updateById(Server resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    Boolean testConnect(Server resources);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<ServerDto> all, HttpServletResponse response) throws IOException;
}
