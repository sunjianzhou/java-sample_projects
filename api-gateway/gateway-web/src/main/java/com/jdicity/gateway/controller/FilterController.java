package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.Filter;
import com.jdicity.gateway.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 10:24
 */

@RestController
@Api(value = "过滤器接口", tags = "过滤器接口")
public class FilterController {
    @Autowired
    private FilterService filterService;

    @GetMapping("/filter/findFilterById/{id}")
    @ApiOperation("根据id查询过滤器")
    public String findFilterById(@PathVariable("id") long id) {
        Filter filterById = null;
        try {
            filterById = filterService.findFilterById(id);
            if (filterById == null) {
                return "访问过滤器信息失败，不存在此过滤器。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "访问过滤器信息失败。";
        }
        return new Gson().toJson(filterById);
    }

    @PostMapping("/filter/addFilter")
    @ApiOperation("添加过滤器")
    @ApiImplicitParam(value = "过滤器", name = "filter", required = true, dataType = "Filter", paramType = "body")
    public String addFilter(@RequestBody Filter filter) {
        try {
            filterService.addFilter(filter);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加过滤器信息失败";
        }
        return new Gson().toJson(filter);
    }

    @PostMapping("/filter/updateFilter")
    @ApiOperation("修改过滤器")
    @ApiImplicitParam(value = "过滤器", name = "filter", required = true, dataType = "Filter", paramType = "body")
    public String updateFilter(@RequestBody Filter filter) {
        try {
            int count = filterService.updateFilter(filter);
            if (count == 0) {
                return "更新过滤器信息失败，不存在对应的过滤器";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新过滤器信息失败";
        }
        return new Gson().toJson(filterService.findFilterById(filter.getId()));
    }

    @PostMapping("/filter/deleteFilter")
    @ApiOperation("删除过滤器")
    @ApiImplicitParam(value = "过滤器", name = "filter", required = true, dataType = "Filter", paramType = "body")
    public String deleteFilter(@RequestBody Filter filter) {
        try {
            int count = filterService.deleteFilter(filter);
            if (count == 0) {
                return "删除过滤器信息失败，不存在对应的过滤器。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除过滤器信息失败。";
        }
        return "删除成功";
    }
}
