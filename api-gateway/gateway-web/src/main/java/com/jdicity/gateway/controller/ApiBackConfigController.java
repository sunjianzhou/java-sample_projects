package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ApiBackConfig;
import com.jdicity.gateway.service.ApiBackConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 22:52
 */

@RestController
@Api(value = "api后端配置接口", tags = "api后端配置接口")
public class ApiBackConfigController {
    @Autowired
    private ApiBackConfigService apiBackConfigService;

    @GetMapping("/backConfig/findBackConfigById/{id}")
    @ApiOperation("根据id查询api后端配置")
    public String findBackConfigById(@PathVariable("id") long id) {
        ApiBackConfig apiBackConfigById = null;
        try {
            apiBackConfigById = apiBackConfigService.findApiBackConfigById(id);
            if (apiBackConfigById == null) {
                return "查询失败，没有相应的后端配置。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(apiBackConfigById);
    }

    @PostMapping("/backConfig/addBackConfig")
    @ApiOperation("添加api后端配置")
    @ApiImplicitParam(value = "api后端配置", name = "apiBackConfig", required = true, dataType = "ApiBackConfig", paramType = "body")
    public String addBackConfig(@RequestBody ApiBackConfig apiBackConfig) {
        try {
            apiBackConfigService.addApiBackConfig(apiBackConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加后端配置失败。";
        }
        return new Gson().toJson(apiBackConfig);
    }

    @PostMapping("/backConfig/updateBackConfig")
    @ApiOperation("修改api后端配置")
    @ApiImplicitParam(value = "api后端配置", name = "apiBackConfig", required = true, dataType = "ApiBackConfig", paramType = "body")
    public String updateBackConfig(@RequestBody ApiBackConfig apiBackConfig) {
        try {
            int count = apiBackConfigService.updateApiBackConfig(apiBackConfig);
            if (count == 0) {
                return "更新失败，没有相应的后端配置。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败。";
        }
        return new Gson().toJson(apiBackConfigService.findApiBackConfigById(apiBackConfig.getId()));
    }

    @PostMapping("/backConfig/deleteBackConfig")
    @ApiOperation("删除api后端配置")
    @ApiImplicitParam(value = "api后端配置", name = "apiBackConfig", required = true, dataType = "ApiBackConfig", paramType = "body")
    public String deleteBackConfig(@RequestBody ApiBackConfig apiBackConfig) {
        try {
            int count = apiBackConfigService.deleteApiBackConfig(apiBackConfig);
            if (count == 0) {
                return "删除失败，没有相应的后端配置。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败。";
        }
        return "删除成功。";
    }
}
