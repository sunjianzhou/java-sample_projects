package com.jdicity.gateway.controller;

import com.jdicity.gateway.entity.ApiInfo;
import com.jdicity.gateway.service.ApiInfoService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 18:11
 */

@RestController
@Api(value = "api基本信息接口", tags = "api基本信息接口")
public class ApiInfoController {
    @Autowired
    private ApiInfoService apiInfoService;

    @GetMapping("/apiInfo/findApiInfoById/{id}")
    @ApiOperation("根据id查询api的基本信息")
    public String findApiInfoById(@PathVariable("id") long id) {
        ApiInfo apiInfoById = null;
        try {
            apiInfoById = apiInfoService.findApiInfoById(id);
            if (apiInfoById == null) {
                return "访问API信息失败，不存在此API。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "访问API信息失败。";
        }
        return new Gson().toJson(apiInfoById);
    }

    @PostMapping("/apiInfo/addApiInfo")
    @ApiOperation("添加api基本信息")
    @ApiImplicitParam(value = "api基本信息", name = "apiInfo", required = true, dataType = "ApiInfo", paramType = "body")
    public String addApiInfo(@RequestBody ApiInfo apiInfo) {
        try {
            if ("".equals(apiInfo.getApiName())) {
                return "apiName不能为空。";
            }
            apiInfoService.addApiInfo(apiInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加API信息失败";
        }
        return new Gson().toJson(apiInfo);
    }

    @PostMapping("/apiInfo/updateApiInfo")
    @ApiOperation("修改api基本信息")
    @ApiImplicitParam(value = "api基本信息", name = "apiInfo", required = true, dataType = "ApiInfo", paramType = "body")
    public String updateApiInfo(@RequestBody ApiInfo apiInfo) {
        try {
            int count = apiInfoService.updateApiInfo(apiInfo);
            if (count == 0) {
                return "更新API信息失败，不存在对应的API";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新API信息失败";
        }
        return new Gson().toJson(apiInfoService.findApiInfoById(apiInfo.getId()));
    }

    @PostMapping("/apiInfo/deleteApiInfo")
    @ApiOperation("删除api基本信息")
    @ApiImplicitParam(value = "api基本信息", name = "apiInfo", required = true, dataType = "ApiInfo", paramType = "body")
    public String deleteApiInfo(@RequestBody ApiInfo apiInfo) {
        try {
            int count = apiInfoService.deleteApiInfo(apiInfo);
            if (count == 0) {
                return "删除API信息失败，不存在对应的API。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除API信息失败。";
        }
        return "删除成功";
    }
}
