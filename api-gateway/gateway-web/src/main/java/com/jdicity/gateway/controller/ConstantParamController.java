package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.ConstantParameter;
import com.jdicity.gateway.service.ConstantParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 0:14
 */

@RestController
@Api(value = "api常量参数接口", tags = "api常量参数接口")
public class ConstantParamController {
    @Autowired
    private ConstantParamService constantParamService;

    @GetMapping("/constantParam/findConstantParamById/{id}")
    @ApiOperation("根据id查询api常量参数")
    public String findConstantParamById(@PathVariable("id") long id) {
        ConstantParameter constantParameterById = null;
        try {
            constantParameterById = constantParamService.findConstantParameterById(id);
            if (constantParameterById == null) {
                return "查询失败，没有相关的常量参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(constantParameterById);
    }

    @PostMapping("/constantParam/addConstantParam")
    @ApiOperation("添加api常量参数")
    @ApiImplicitParam(value = "api常量参数", name = "constantParameter", required = true, dataType = "ConstantParameter", paramType = "body")
    public String addConstantParam(@RequestBody ConstantParameter constantParameter) {
        try {
            constantParamService.addConstantParameter(constantParameter);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加常量参数失败。";
        }
        return new Gson().toJson(constantParameter);
    }

    @PostMapping("/constantParam/updateConstantParam")
    @ApiOperation("修改api常量参数")
    @ApiImplicitParam(value = "api常量参数", name = "constantParameter", required = true, dataType = "ConstantParameter", paramType = "body")
    public String updateConstantParam(@RequestBody ConstantParameter constantParameter) {
        try {
            int count = constantParamService.updateConstantParameter(constantParameter);
            if (count == 0) {
                return "更新失败，没有相应的常量参数。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败。";
        }
        return new Gson().toJson(constantParamService.findConstantParameterById(constantParameter.getId()));
    }

    @PostMapping("/constantParam/deleteConstantParam")
    @ApiOperation("删除api常量参数")
    @ApiImplicitParam(value = "api常量参数", name = "constantParameter", required = true, dataType = "ConstantParameter", paramType = "body")
    public String deleteConstantParam(@RequestBody ConstantParameter constantParameter) {
        try {
            int count = constantParamService.deleteConstantParameter(constantParameter);
            if (count == 0) {
                return "删除失败，没有相应的常量参数。";
            }
        } catch (Exception e) {
            return "删除失败。";
        }
        return "删除成功。";
    }
}
