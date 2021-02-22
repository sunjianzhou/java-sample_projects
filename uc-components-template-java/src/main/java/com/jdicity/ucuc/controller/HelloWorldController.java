package com.jdicity.ucuc.controller;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.schemas.common.UniversalResponse;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestCreate;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestRead;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestUpdate;
import com.jdicity.ucuc.schemas.response.HelloWorldCudVo;
import com.jdicity.ucuc.schemas.response.HelloWorldReadVo;
import com.jdicity.ucuc.service.HelloWorldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * Hello world API.
 *
 * @author liyingda
 * @version v1.0.0
 * @date 2020-11-16 15:30
 */
@Api(tags = "hello-world模板")
@Slf4j
@RestController
@RequestMapping("/hello-world-batch")
public class HelloWorldController {
    /**
     * Hello world 服务.
     */
    @Resource
    private HelloWorldService helloWorldService;

    /**
     * Create hello world data in batch.
     *
     * @param helloWorldRequestCreate The body param of add hello world in batch
     * @return The ids of hello world created just now.
     */
    @ApiOperation("新增 hello world")
    @PostMapping()
    public UniversalResponse<List<HelloWorldCudVo>> helloWorldPost(
            @RequestBody @Validated List<HelloWorldRequestCreate> helloWorldRequestCreate) {
        return new UniversalResponse<>(ResponseCodeEnum.SUCCESS,
                helloWorldService.createHelloWorld(helloWorldRequestCreate));
    }

    /**
     * Update hello world data in batch.
     *
     * @param helloWorldRequestUpdateList The body param of update hello world in batch
     * @return Return status code.
     */
    @ApiOperation("根据dataId更新 hello world")
    @PutMapping()
    public UniversalResponse<List<HelloWorldCudVo>> updateHelloWorldByDataId(
            @RequestBody @Validated List<HelloWorldRequestUpdate> helloWorldRequestUpdateList) {
        List<HelloWorldCudVo> helloWorldCudVos = helloWorldService.updateHelloWorldByDataId(helloWorldRequestUpdateList);
        return new UniversalResponse<>(ResponseCodeEnum.SUCCESS, helloWorldCudVos);
    }

    /**
     * Delete hello world data in batch.
     *
     * @param dataList dataId set of Hello world to be deleted
     * @return status code
     */
    @ApiOperation("根据dataId删除 hello world")
    @DeleteMapping()
    public UniversalResponse<List<HelloWorldCudVo>> deletedByDataId(
            @RequestBody @Validated List<String> dataList) {
        List<HelloWorldCudVo> helloWorldCudVos = helloWorldService.deletedByDataId(dataList);
        return new UniversalResponse<>(ResponseCodeEnum.SUCCESS, helloWorldCudVos);
    }

    /**
     * Get hello world data by page.
     *
     * @param queryParams The query param of get hello world in batch
     * @return The details of hello worlds
     */
    @ApiOperation("查询 hello world")
    @GetMapping("/page")
    public UniversalResponse<Object> helloWorldGetPage(
            @ModelAttribute HelloWorldRequestRead queryParams) {
        return UniversalResponse.createSuccess(helloWorldService.pageHelloWorlds(queryParams));
    }

    /**
     * Get all hello world data.
     *
     * @return The details of hello worlds
     */
    @ApiOperation("查询所有 hello world")
    @GetMapping("/findAll")
    public UniversalResponse<List<HelloWorldReadVo>> findAllHelloWorld() {
        List<HelloWorldReadVo> list = helloWorldService.findAll();
        return new UniversalResponse<>(ResponseCodeEnum.SUCCESS, list);
    }

}
