
package com.ikun.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ikun.base.impl.CommonServiceImpl;
import com.ikun.domain.GenConfig;
import com.ikun.service.mapper.GenConfigMapper;
import com.ikun.service.GenConfigService;
import com.ikun.utils.StringUtils;
import org.springframework.stereotype.Service;
import java.io.File;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends CommonServiceImpl<GenConfigMapper, GenConfig> implements GenConfigService {

    private final GenConfigMapper genConfigMapper;

    @Override
    public GenConfig find(String tableName) {
        GenConfig genConfig = lambdaQuery()
                .eq(GenConfig::getTableName, tableName)
                .one();
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if(StringUtils.isBlank(genConfig.getApiPath())){
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        if (genConfig.getId() == null) {
            genConfigMapper.insert(genConfig);
        } else {
            genConfigMapper.updateById(genConfig);
        }
        return genConfig;
    }
}
