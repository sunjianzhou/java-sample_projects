package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.Application;
import com.jdicity.gateway.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 10:44
 */

@RestController
@Api(value = "应用管理接口", tags = "应用管理接口")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/application/findApplicationById/{id}")
    @ApiOperation("根据id查找一个应用")
    public String findApplicationById(@PathVariable("id") long id) {
        Application applicationById = null;
        try {
            applicationById = applicationService.findApplicationById(id);
            if (applicationById == null) {
                return "访问应用信息失败，不存在此应用。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "访问应用信息失败。";
        }
        return new Gson().toJson(applicationById);
    }

    @PostMapping("/application/addApplication")
    @ApiOperation("创建一个应用")
    @ApiImplicitParam(value = "应用", name = "application", required = true, dataType = "Application", paramType = "body")
    public String addApplication(@RequestBody Application application) {
        try {
            applicationService.addApplication(application);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加应用信息失败";
        }
        return new Gson().toJson(application);
    }

    @PostMapping("/application/updateApplication")
    @ApiOperation("修改一个应用")
    @ApiImplicitParam(value = "应用", name = "application", required = true, dataType = "Application", paramType = "body")
    public String updateApplication(@RequestBody Application application) {
        try {
            int count = applicationService.updateApplication(application);
            if (count == 0) {
                return "更新应用信息失败，不存在对应的应用";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新应用信息失败";
        }
        return new Gson().toJson(applicationService.findApplicationById(application.getId()));
    }

    @PostMapping("/application/deleteApplication")
    @ApiOperation("删除一个应用")
    @ApiImplicitParam(value = "应用", name = "application", required = true, dataType = "Application", paramType = "body")
    public String deleteApplication(@RequestBody Application application) {
        try {
            int count = applicationService.deleteApplication(application);
            if (count == 0) {
                return "删除应用信息失败，不存在对应的应用。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除应用信息失败。";
        }
        return "删除成功";
    }
}
