package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ApiFrontConfig;
import com.jdicity.gateway.service.ApiFrontConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 20:55
 */
@RestController
@Api(value = "api前端配置接口", tags = "api前端配置接口")
public class ApiFrontConfigController {
    @Autowired
    private ApiFrontConfigService apiFrontConfigService;

    @GetMapping("/frontConfig/findFrontConfigById/{id}")
    @ApiOperation("根据id查询api的前端配置")
    public String findFrontConfigById(@PathVariable("id") long id) {
        ApiFrontConfig apiFrontConfigById = null;
        try {
            apiFrontConfigById = apiFrontConfigService.findApiFrontConfigById(id);
            if (apiFrontConfigById == null) {
                return "查询失败，没有相关的前端配置。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(apiFrontConfigById);
    }

    @PostMapping("/frontConfig/addFrontConfig")
    @ApiOperation("添加api前端配置")
    @ApiImplicitParam(value = "api前端配置", name = "apiFrontConfig", required = true, dataType = "ApiFrontConfig", paramType = "body")
    public String addFrontConfig(@RequestBody ApiFrontConfig apiFrontConfig) {
        try {
            apiFrontConfigService.addApiFrontConfig(apiFrontConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加前端配置失败。";
        }
        return new Gson().toJson(apiFrontConfig);
    }

    @PostMapping("/frontConfig/updateFrontConfig")
    @ApiOperation("修改api前端配置")
    @ApiImplicitParam(value = "api前端配置", name = "apiFrontConfig", required = true, dataType = "ApiFrontConfig", paramType = "body")
    public String updateFrontConfig(@RequestBody ApiFrontConfig apiFrontConfig) {
        try {
            int count = apiFrontConfigService.updateApiFrontConfig(apiFrontConfig);
            if (count == 0) {
                return "更新失败，没有相应的前端配置。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败。";
        }
        return new Gson().toJson(apiFrontConfigService.findApiFrontConfigById(apiFrontConfig.getId()));
    }

    @PostMapping("/frontConfig/deleteFrontConfig")
    @ApiOperation("删除api前端配置")
    @ApiImplicitParam(value = "api前端配置", name = "apiFrontConfig", required = true, dataType = "ApiFrontConfig", paramType = "body")
    public String deleteFrontConfig(@RequestBody ApiFrontConfig apiFrontConfig) {
        try {
            int count = apiFrontConfigService.deleteApiFrontConfig(apiFrontConfig);
            if (count == 0) {
                return "删除失败，没有相应的前端配置。";
            }
        } catch (Exception e) {
            return "删除失败。";
        }
        return "删除成功。";
    }
}
