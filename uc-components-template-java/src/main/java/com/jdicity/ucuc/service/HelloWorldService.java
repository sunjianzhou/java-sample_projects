package com.jdicity.ucuc.service;

import com.jdicity.ucuc.model.HelloWorld;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestCreate;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestRead;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestUpdate;
import com.jdicity.ucuc.schemas.response.HelloWorldCudVo;
import com.jdicity.ucuc.schemas.response.HelloWorldReadVo;
import com.jdicity.ucuc.schemas.common.PaginationResponse;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * Hello world Service Interface.
 *
 * @author liyingda
 * @date 2020-11-16 15:30
 * @version V1.0.0
 */
public interface HelloWorldService extends IService<HelloWorld> {
    /**
     * 批量添加.
     *
     * @param helloWorldList 要添加的集合
     * @return 添加成功后的dataId
     */
    List<HelloWorldCudVo> createHelloWorld(List<HelloWorldRequestCreate> helloWorldList);

    /**
     * 分页条件查询.
     *
     * @param queryParams 参数
     * @return 分页数据
     */
    PaginationResponse<HelloWorldReadVo> pageHelloWorlds(HelloWorldRequestRead queryParams);

    /**
     * 查询所有有效数据.
     *
     * @return 所有有效数据集合
     */
    List<HelloWorldReadVo> findAll();

    /**
     * 根据dataId批量删除.
     *
     * @param dataList dataId集合
     */
    List<HelloWorldCudVo> deletedByDataId(List<String> dataList);

    /**
     * 根据dataId批量修改.
     *
     * @param helloWorldRequestUpdateList HelloWorldRequestUpdate
     */
    List<HelloWorldCudVo> updateHelloWorldByDataId(List<HelloWorldRequestUpdate> helloWorldRequestUpdateList);
}
