package com.ikun.modules.mnt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ikun.modules.mnt.service.dto.ServerDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import com.ikun.base.PageInfo;
import com.ikun.base.QueryHelpMybatisPlus;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.modules.mnt.util.ExecuteShellUtil;
import com.ikun.utils.FileUtil;
import com.ikun.utils.ConvertUtil;
import com.ikun.modules.mnt.domain.Server;
import com.ikun.modules.mnt.service.ServerService;
import com.ikun.modules.mnt.service.dto.ServerQueryParam;
import com.ikun.modules.mnt.mapper.ServerMapper;
import com.ikun.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author jinjin
* @date 2020-09-27
*/
@Slf4j
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = ServerService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServerServiceImpl extends CommonServiceImpl<ServerMapper, Server> implements ServerService {

    // private final RedisUtils redisUtils;
    private final ServerMapper serverMapper;

    @Override
    public PageInfo<ServerDto> queryAll(ServerQueryParam query, Pageable pageable) {
        IPage<Server> page = PageUtil.toMybatisPage(pageable);
        IPage<Server> pageList = serverMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, ServerDto.class);
    }

    @Override
    public List<ServerDto> queryAll(ServerQueryParam query){
        return ConvertUtil.convertList(serverMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), ServerDto.class);
    }

    @Override
    public Server getById(Long id) {
        return serverMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public ServerDto findById(Long id) {
        return ConvertUtil.convert(getById(id), ServerDto.class);
    }

    @Override
    public ServerDto findByIp(String ip) {
        return ConvertUtil.convert(lambdaQuery().eq(Server::getIp, ip).one(), ServerDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Server resources) {
        return serverMapper.insert(resources) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Server resources){
        int ret = serverMapper.updateById(resources);
        // delCaches(resources.id);
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids){
        // delCaches(ids);
        return serverMapper.deleteBatchIds(ids) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/
    @Override
    public Boolean testConnect(Server resources) {
        ExecuteShellUtil executeShellUtil = null;
        try {
            executeShellUtil = new ExecuteShellUtil(resources.getIp(), resources.getAccount(), resources.getPassword(),resources.getPort());
            return executeShellUtil.execute("ls")==0;
        } catch (Exception e) {
            return false;
        }finally {
            if (executeShellUtil != null) {
                executeShellUtil.close();
            }
        }
    }

    @Override
    public void download(List<ServerDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (ServerDto server : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("账号", server.getAccount());
              map.put("IP地址", server.getIp());
              map.put("名称", server.getName());
              map.put("密码", server.getPassword());
              map.put("端口", server.getPort());
              map.put("创建者", server.getCreateBy());
              map.put("更新者", server.getUpdateBy());
              map.put("创建时间", server.getCreateTime());
              map.put("更新时间", server.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }
}
