
package com.ikun.modules.mnt.rest;

import com.ikun.modules.mnt.domain.App;
import com.ikun.modules.mnt.service.AppService;
import com.ikun.modules.mnt.service.dto.AppQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ikun.annotation.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "运维：应用管理")
@RequestMapping("/api/app")
public class AppController {

    private final AppService appService;

    @ApiOperation("导出应用数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('app:list')")
    public void download(HttpServletResponse response, AppQueryParam criteria) throws IOException {
        appService.download(appService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询应用")
    @GetMapping
	@PreAuthorize("@el.check('app:list')")
    public ResponseEntity<Object> query(AppQueryParam criteria, Pageable pageable){
        return new ResponseEntity<>(appService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增应用")
    @ApiOperation(value = "新增应用")
    @PostMapping
	@PreAuthorize("@el.check('app:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody App resources){
        appService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改应用")
    @ApiOperation(value = "修改应用")
    @PutMapping
	@PreAuthorize("@el.check('app:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody App resources){
        appService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除应用")
    @ApiOperation(value = "删除应用")
	@DeleteMapping
	@PreAuthorize("@el.check('app:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        appService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
