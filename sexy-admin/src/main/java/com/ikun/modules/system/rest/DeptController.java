
package com.ikun.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.ikun.modules.system.domain.Dept;
import com.ikun.modules.system.service.DeptService;
import com.ikun.modules.system.service.dto.DeptDto;
import com.ikun.modules.system.service.dto.DeptQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ikun.annotation.Log;
import com.ikun.exception.BadRequestException;
import com.ikun.utils.PageUtil;
import com.ikun.utils.ThrowableUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    private final DeptService deptService;
    private static final String ENTITY_NAME = "dept";

    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dept:list')")
    public void download(HttpServletResponse response, DeptQueryParam criteria) throws Exception {
        deptService.download(deptService.queryAll(criteria, false), response);
    }

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','dept:list')")
    public ResponseEntity<Object> query(DeptQueryParam criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(deptDtos, deptDtos.size()),HttpStatus.OK);
    }

    @ApiOperation("查询部门:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    @PreAuthorize("@el.check('user:list','dept:list')")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<DeptDto> deptDtos  = new LinkedHashSet<>();
        for (Long id : ids) {
            DeptDto deptDto = deptService.findById(id);
            List<DeptDto> depts = deptService.getSuperior(deptDto, new ArrayList<>());
            deptDtos.addAll(depts);
        }
        return new ResponseEntity<>(deptService.buildTree(new ArrayList<>(deptDtos)),HttpStatus.OK);
    }

    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        deptService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('dept:edit')")
    public ResponseEntity<Object> update(@Validated(Dept.Update.class) @RequestBody Dept resources){
        deptService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('dept:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        Set<Long> deptIds = new HashSet<>();
        for (Long id : ids) {
            List<Dept> deptList = deptService.findByPid(id);
            deptIds.add(deptService.findById(id).getId());
            if(CollectionUtil.isNotEmpty(deptList)){
                deptIds = deptService.getDeleteDepts(deptList, deptIds);
            }
        }
        // 验证是否被角色或用户关联
        deptService.verification(deptIds);
        deptService.removeByIds(deptIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
