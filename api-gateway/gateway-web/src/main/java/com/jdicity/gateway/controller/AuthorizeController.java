package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ApiApp;
import com.jdicity.gateway.service.AuthorizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 11:07
 */

@RestController
@Api(value = "授权管理接口", tags = "授权管理接口")
public class AuthorizeController {
    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping("/authorize/findAuthorizeById/{id}")
    @ApiOperation("根据id查询授权信息")
    public String findApplicationById(@PathVariable("id") long id) {
        ApiApp apiAppById = null;
        try {
            apiAppById = authorizeService.findApiAppById(id);
            if (apiAppById == null) {
                return "访问授权信息失败，不存在此授权信息。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "访问授权信息失败。";
        }
        return new Gson().toJson(apiAppById);
    }

    @PostMapping("/authorize/addAuthorize")
    @ApiOperation("创建一个授权")
    @ApiImplicitParam(value = "授权表", name = "apiApp", required = true, dataType = "ApiApp", paramType = "body")
    public String addAuthorize(@RequestBody ApiApp apiApp) {
        try {
            authorizeService.addApiApp(apiApp);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加授权信息失败";
        }
        return new Gson().toJson(apiApp);
    }

    @PostMapping("/authorize/updateAuthorize")
    @ApiOperation("修改一个授权")
    @ApiImplicitParam(value = "授权表", name = "apiApp", required = true, dataType = "ApiApp", paramType = "body")
    public String updateAuthorize(@RequestBody ApiApp apiApp) {
        try {
            int count = authorizeService.updateApiApp(apiApp);
            if (count == 0) {
                return "更新授权信息失败，不存在对应的授权信息。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新授权信息失败。";
        }
        return new Gson().toJson(authorizeService.findApiAppById(apiApp.getId()));
    }

    @PostMapping("/authorize/deleteAuthorize")
    @ApiOperation("删除一个授权")
    @ApiImplicitParam(value = "授权表", name = "apiApp", required = true, dataType = "ApiApp", paramType = "body")
    public String deleteAuthorize(@RequestBody ApiApp apiApp) {
        try {
            int count = authorizeService.deleteApiApp(apiApp);
            if (count == 0) {
                return "删除授权信息失败，不存在对应的授权。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除授权信息失败。";
        }
        return "删除成功";
    }
}
