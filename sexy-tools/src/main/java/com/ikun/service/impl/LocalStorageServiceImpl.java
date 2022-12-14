
package com.ikun.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import com.ikun.base.QueryHelpMybatisPlus;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.config.FileProperties;
import com.ikun.domain.LocalStorage;
import com.ikun.service.dto.LocalStorageDto;
import com.ikun.service.dto.LocalStorageQueryParam;
import com.ikun.exception.BadRequestException;
import com.ikun.mapper.LocalStorageMapper;
import com.ikun.utils.*;
import com.ikun.service.LocalStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl extends CommonServiceImpl<LocalStorageMapper, LocalStorage> implements LocalStorageService {

    private final FileProperties properties;
    private final LocalStorageMapper localStorageMapper;

    @Override
    public Object queryAll(LocalStorageQueryParam query, Pageable pageable){
        IPage<LocalStorage> page = PageUtil.toMybatisPage(pageable);
        IPage<LocalStorage> pageList = localStorageMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, LocalStorageDto.class);
    }

    @Override
    public List<LocalStorageDto> queryAll(LocalStorageQueryParam query){
        return ConvertUtil.convertList(localStorageMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), LocalStorageDto.class);
    }

    @Override
    public LocalStorageDto findById(Long id){
        return ConvertUtil.convert(getById(id), LocalStorageDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalStorage create(String name, MultipartFile multipartFile) {
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type +  File.separator);
        if(ObjectUtil.isNull(file)){
            throw new BadRequestException("????????????");
        }
        try {
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(
                    file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtil.getSize(multipartFile.getSize())
            );
            localStorageMapper.insert(localStorage);
            return localStorage;
        }catch (Exception e){
            FileUtil.del(file);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage resources) {
        updateById(resources);
        // delCaches(resources.id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            LocalStorage storage = getById(id);
            FileUtil.del(storage.getPath());
            removeById(id);
        }
    }

    @Override
    public void download(List<LocalStorageDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LocalStorageDto localStorageDTO : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("?????????", localStorageDTO.getRealName());
            map.put("?????????", localStorageDTO.getName());
            map.put("????????????", localStorageDTO.getType());
            map.put("????????????", localStorageDTO.getSize());
            map.put("?????????", localStorageDTO.getCreateBy());
            map.put("????????????", localStorageDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
