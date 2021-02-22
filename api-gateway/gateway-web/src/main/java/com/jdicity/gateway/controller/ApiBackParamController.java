package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ApiBackParameter;
import com.jdicity.gateway.service.ApiBackParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 23:39
 */

@RestController
@Api(value = "api后端参数接口", tags = "api后端参数接口")
public class ApiBackParamController {
    @Autowired
    private ApiBackParamService apiBackParamService;

    @GetMapping("/backParam/findBackParamById/{id}")
    @ApiOperation("根据id查询api后端参数")
    public String findBackParamById(@PathVariable("id") long id) {
        ApiBackParameter apiBackParameterById = null;
        try {
            apiBackParameterById = apiBackParamService.findApiBackParameterById(id);
            if (apiBackParameterById == null) {
                return "查询失败，没有相关的后端参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(apiBackParameterById);
    }

    @PostMapping("/backParam/addBackParam")
    @ApiOperation("添加api后端参数")
    @ApiImplicitParam(value = "api后端参数", name = "apiBackParameter", required = true, dataType = "ApiBackParameter", paramType = "body")
    public String addBackParam(@RequestBody ApiBackParameter apiBackParameter) {
        try {
            apiBackParamService.addApiBackParameter(apiBackParameter);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加后端参数失败。";
        }
        return new Gson().toJson(apiBackParameter);
    }

    @PostMapping("/backParam/updateBackParam")
    @ApiOperation("修改api后端参数")
    @ApiImplicitParam(value = "api后端参数", name = "apiBackParameter", required = true, dataType = "ApiBackParameter", paramType = "body")
    public String updateBackParam(@RequestBody ApiBackParameter apiBackParameter) {
        try {
            int count = apiBackParamService.updateApiBackParameter(apiBackParameter);
            if (count == 0) {
                return "更新失败，没有相应的后端参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败。";
        }
        return new Gson().toJson(apiBackParamService.findApiBackParameterById(apiBackParameter.getId()));
    }

    @PostMapping("/backParam/deleteBackParam")
    @ApiOperation("删除api后端参数")
    @ApiImplicitParam(value = "api后端参数", name = "apiBackParameter", required = true, dataType = "ApiBackParameter", paramType = "body")
    public String deleteBackParam(@RequestBody ApiBackParameter apiBackParameter) {
        try {
            int count = apiBackParamService.deleteApiBackParameter(apiBackParameter);
            if (count == 0) {
                return "删除失败，没有相应的后端参数。";
            }
        } catch (Exception e) {
            return "删除失败。";
        }
        return "删除成功。";
    }
}
