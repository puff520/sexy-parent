
package com.ikun.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.ikun.base.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * 分页工具
 * @author Zheng Jie
 * @date 2018-12-10
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * List 分页
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     */
    public static Map<String,Object> toPage(Page page) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",page.getContent());
        map.put("totalElements",page.getTotalElements());
        return map;
    }

    /**
     * 自定义分页
     */
    public static <T> PageInfo<T> toPage(List<T> object, long totalElements) {
        PageInfo<T> page = new PageInfo<>();
        page.setContent(object);
        page.setTotalElements(totalElements);
        return page;
    }

    public static <T> IPage<T> toMybatisPage(Pageable pageable) {
        return toMybatisPage(pageable, false);
    }

    public static <T> IPage<T> toMybatisPage(Pageable pageable, boolean ignoreOrderBy) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        if (!ignoreOrderBy) {
            for (Sort.Order order : pageable.getSort()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setAsc(order.isAscending());
                orderItem.setColumn(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(order.getProperty()));
                page.addOrder(orderItem);
            }
        }
        return page;
    }
}
