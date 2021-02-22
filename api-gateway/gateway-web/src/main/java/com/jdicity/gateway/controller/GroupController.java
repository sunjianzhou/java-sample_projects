package com.jdicity.gateway.controller;

import com.jdicity.gateway.entity.ServiceGroup;
import com.jdicity.gateway.service.GroupService;
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
 * @date 2020/12/17 15:09
 */

@RestController
@Api(value = "分组管理接口", tags = "分组管理接口")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/group/findGroupById/{id}")
    @ApiOperation("根据id查询分组")
    public String findGroupById(@PathVariable("id") long groupId) {
        ServiceGroup groupById = null;
        try {
            groupById = groupService.findGroupById(groupId);
            if (groupById == null) {
                return "查询失败，不存在对应的组。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败。";
        }
        return new Gson().toJson(groupById);
    }

    @PostMapping("/group/addGroup")
    @ApiOperation("创建一个分组")
    @ApiImplicitParam(value = "组", name = "group", required = true, dataType = "Service", paramType = "body")
    public String addGroup(@RequestBody ServiceGroup group) {
        try {
            groupService.addGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加组失败";
        }
        return new Gson().toJson(group);
    }

    @PostMapping("/group/updateGroup")
    @ApiOperation("修改组信息")
    @ApiImplicitParam(value = "组", name = "group", required = true, dataType = "Service", paramType = "body")
    public String updateGroup(@RequestBody ServiceGroup group) {
        try {
            int count = groupService.updateGroup(group);
            if (count == 0) {
                return "更新组失败，不存在对应的组。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "更新组失败。";
        }
        return new Gson().toJson(groupService.findGroupById(group.getId()));
    }

    @PostMapping("/group/deleteGroup")
    @ApiOperation("删除一个分组")
    @ApiImplicitParam(value = "组", name = "group", required = true, dataType = "Service", paramType = "body")
    public String deleteGroup(@RequestBody ServiceGroup group) {
        try {
            int count = groupService.deleteGroup(group);
            if (count == 0) {
                return "删除失败，不存在对应的组";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败。";
        }
        return "删除成功。";
    }
}
