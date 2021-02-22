package com.jdicity.gateway.controller;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.Sentinel;
import com.jdicity.gateway.service.SentinelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/23 20:40
 */

@RestController
@Api(value = "限流降级管理接口", tags = "限流降级管理接口")
public class SentinelController {
    @Autowired
    private SentinelService sentinelService;

    @GetMapping("/sentinel/findSentinelById/{id}")
    @ApiOperation("根据id查询限流降级规则")
    public String findSentinelById(@PathVariable("id") long sentinelId) {
        Sentinel sentinelById = null;
        try {
            sentinelById = sentinelService.findSentinelById(sentinelId);
            if (sentinelById == null) {
                return "查询限流降级规则失败。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询限流降级规则失败。";
        }
        return new Gson().toJson(sentinelById);
    }

    @PostMapping("/sentinel/addSentinel")
    @ApiOperation("添加限流降级规则")
    @ApiImplicitParam(value = "限流降级规则", name = "sentinel", required = true, dataType = "Sentinel", paramType = "body")
    public String addSentinel(@RequestBody Sentinel sentinel) {
        try {
            sentinelService.addSentinel(sentinel);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加限流降级规则失败。";
        }
        return new Gson().toJson(sentinel);
    }

    @PostMapping("/sentinel/updateSentinel")
    @ApiOperation("更新限流降级规则")
    @ApiImplicitParam(value = "限流降级规则", name = "sentinel", required = true, dataType = "Sentinel", paramType = "body")
    public String updateSentinel(@RequestBody Sentinel sentinel) {
        try {
            int count = sentinelService.updateSentinel(sentinel);
            if (count == 0) {
                return "更新失败，不存在对应的限流降级规则。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新限流降级规则失败。";
        }
        return new Gson().toJson(sentinelService.findSentinelById(sentinel.getId()));
    }

    @PostMapping("/sentinel/deleteSentinel")
    @ApiOperation("删除一个限流降级规则")
    @ApiImplicitParam(value = "限流降级规则", name = "sentinel", required = true, dataType = "Sentinel", paramType = "body")
    public String deleteSentinel(@RequestBody Sentinel sentinel) {
        try {
            int count = sentinelService.deleteSentinel(sentinel);
            if (count == 0) {
                return "删除失败，不存在对应的限流降级规则。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除限流降级规则失败。";
        }
        return "删除成功";
    }

}
