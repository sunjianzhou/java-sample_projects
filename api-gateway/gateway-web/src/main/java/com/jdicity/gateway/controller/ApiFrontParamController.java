package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ApiFrontParameter;
import com.jdicity.gateway.service.ApiFrontParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 22:13
 */

@RestController
@Api(value = "api前端参数接口", tags = "api前端参数接口")
public class ApiFrontParamController {
    @Autowired
    private ApiFrontParamService apiFrontParamService;

    @GetMapping("/frontParam/findApiFrontParamById/{id}")
    @ApiOperation("根据id查询api前端参数")
    public String findApiFrontParamById(@PathVariable("id") long id) {
        ApiFrontParameter apiFrontParameterById = null;
        try {
            apiFrontParameterById = apiFrontParamService.findApiFrontParameterById(id);
            if (apiFrontParameterById == null) {
                return "查询失败，没有相应的前端参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(apiFrontParameterById);
    }

    @PostMapping("/frontParam/addApiFrontParam")
    @ApiOperation("添加api前端参数")
    @ApiImplicitParam(value = "api前端参数", name = "apiFrontParameter", required = true, dataType = "ApiFrontParameter", paramType = "body")
    public String addApiFrontParam(@RequestBody ApiFrontParameter apiFrontParameter) {
        try {
            apiFrontParamService.addApiFrontParameter(apiFrontParameter);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加前端参数失败。";
        }
        return new Gson().toJson(apiFrontParameter);
    }

    @PostMapping("/frontParam/updateApiFrontParam")
    @ApiOperation("修改api前端参数")
    @ApiImplicitParam(value = "api前端参数", name = "apiFrontParameter", required = true, dataType = "ApiFrontParameter", paramType = "body")
    public String updateApiFrontParam(@RequestBody ApiFrontParameter apiFrontParameter) {
        try {
            int count = apiFrontParamService.updateApiFrontParameter(apiFrontParameter);
            if (count == 0) {
                return "更新失败，没有相应的前端参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败。";
        }
        return new Gson().toJson(apiFrontParamService.findApiFrontParameterById(apiFrontParameter.getId()));
    }

    @PostMapping("/frontParam/deleteApiFrontParam")
    @ApiOperation("删除api前端参数")
    @ApiImplicitParam(value = "api前端参数", name = "apiFrontParameter", required = true, dataType = "ApiFrontParameter", paramType = "body")
    public String deleteApiFrontParam(@RequestBody ApiFrontParameter apiFrontParameter) {
        try {
            int count = apiFrontParamService.deleteApiFrontParameter(apiFrontParameter);
            if (count == 0) {
                return "删除失败，没有相应的前端参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败。";
        }
        return "删除成功";
    }
}
