
package com.ikun.modules.mnt.rest;

import com.ikun.modules.mnt.service.DeployHistoryService;
import com.ikun.modules.mnt.service.dto.DeployHistoryQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ikun.annotation.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "运维：部署历史管理")
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

    private final DeployHistoryService deployhistoryService;

    @ApiOperation("导出部署历史数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('deployHistory:list')")
    public void download(HttpServletResponse response, DeployHistoryQueryParam criteria) throws IOException {
        deployhistoryService.download(deployhistoryService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询部署历史")
    @GetMapping
	@PreAuthorize("@el.check('deployHistory:list')")
    public ResponseEntity<Object> query(DeployHistoryQueryParam criteria, Pageable pageable){
        return new ResponseEntity<>(deployhistoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("删除DeployHistory")
    @ApiOperation(value = "删除部署历史")
	@DeleteMapping
    @PreAuthorize("@el.check('deployHistory:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> ids){
        deployhistoryService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
