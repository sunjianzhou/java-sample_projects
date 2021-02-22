package com.jdicity.ucuc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdicity.ucuc.constant.Database;
import com.jdicity.ucuc.constant.Schema;
import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.dao.HelloWorldMapper;
import com.jdicity.ucuc.exc.BusinessException;
import com.jdicity.ucuc.interceptor.threadlocals.InspectionContext;
import com.jdicity.ucuc.model.BaseModel;
import com.jdicity.ucuc.model.HelloWorld;
import com.jdicity.ucuc.schemas.common.PaginationResponse;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestCreate;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestRead;
import com.jdicity.ucuc.schemas.request.HelloWorldRequestUpdate;
import com.jdicity.ucuc.schemas.response.HelloWorldCudVo;
import com.jdicity.ucuc.schemas.response.HelloWorldReadVo;
import com.jdicity.ucuc.service.HelloWorldService;
import com.jdicity.ucuc.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Hello world Service Implement.
 *
 * @author liyingda
 * @version V1.0.0
 * @date 2020-11-18 15:30
 */
@Slf4j
@Service
public class HelloWorldServiceImpl extends ServiceImpl<HelloWorldMapper, HelloWorld>
        implements HelloWorldService {

    @Autowired
    public HelloWorldMapper helloWorldMapper;

    /**
     * 批量新增hello world实现.
     *
     * @param helloWorldList 新增参数
     * @return 新增hello world的id集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<HelloWorldCudVo> createHelloWorld(List<HelloWorldRequestCreate> helloWorldList) {
        Long tenantId = InspectionContext.get().getTenantId();
        log.info("当前租户ID为 {}", tenantId);

        List<HelloWorld> helloWorlds = BeanUtil.map2List(helloWorldList, HelloWorld.class);
        if (helloWorlds.size() > 0) {
            helloWorlds.forEach(item -> {
                item.setDataId(UUID.randomUUID().toString().replace("-", ""));
                item.setTenantId(Math.toIntExact(tenantId));
                helloWorldMapper.insert(item);
            });
        }
        boolean saveBatch = this.saveBatch(helloWorlds);
        log.info("批量添加 helloWorld:{}", saveBatch);
        if (!saveBatch) {
            throw new BusinessException(ResponseCodeEnum.BATCH_ADD_FAILED);
        }
        return BeanUtil.map2List(helloWorlds, HelloWorldCudVo.class);
    }

    /**
     * 分页查询hello world实现.
     *
     * @param queryParams 查询参数
     * @return 查询结果
     */
    @SuppressWarnings({"unchecked", "varargs"})
    @Override
    public PaginationResponse<HelloWorldReadVo> pageHelloWorlds(
            @ModelAttribute HelloWorldRequestRead queryParams) {
        Long tenantId = InspectionContext.get().getTenantId();
        log.info("当前租户ID为 {}", tenantId);

        LambdaQueryWrapper<HelloWorld> queryWrapper = new QueryWrapper<HelloWorld>().lambda()
                .eq(HelloWorld::getTenantId, tenantId)
                .eq(HelloWorld::getDeleted, Database.DELETED_FALSE)
                .eq(ObjectUtils.isNotEmpty(queryParams.getStatus()),
                        HelloWorld::getStatus, queryParams.getStatus())
                .in(ObjectUtils.isNotEmpty(queryParams.getDataIds()),
                        HelloWorld::getDataId, queryParams.getDataIds())
                .like(ObjectUtils.isNotEmpty(queryParams.getName()),
                        HelloWorld::getHelloWorldName, queryParams.getName());

        List<String> sortFields = queryParams.getSort();
        if (null != sortFields && sortFields.size() > 0) {
            for (String sortField : sortFields) {
                String[] field = sortField.split(Schema.SORT_SEPARATOR);
                queryWrapper = queryWrapper
                        .orderBy(field[0].equals(Database.FIELD_CREATED),
                                field[1].equals(Schema.SORT_ORDER_ASC), HelloWorld::getCreated)
                        .orderBy(field[0].equals(Database.FIELD_UPDATED),
                                field[1].equals(Schema.SORT_ORDER_ASC), HelloWorld::getUpdated);
            }
        }

        Page<HelloWorld> paramPage = new Page<>(queryParams.getPage(), queryParams.getPageSize());
        IPage<HelloWorld> pageRes = this.page(paramPage, queryWrapper);
        PaginationResponse<HelloWorldReadVo> pageResponse = new PaginationResponse<>();
        pageResponse.setPage((int) pageRes.getCurrent());
        pageResponse.setPageSize((int) pageRes.getSize());
        pageResponse.setTotal((int) pageRes.getTotal());
        pageResponse.setList(BeanUtil.map2List(pageRes.getRecords(), HelloWorldReadVo.class));

        return pageResponse;
    }

    @Override
    public List<HelloWorldReadVo> findAll() {
        List<HelloWorld> list = this.list(new QueryWrapper<HelloWorld>()
                .lambda()
                .eq(BaseModel::getDeleted, Database.DELETED_FALSE));
        return BeanUtil.map2List(list, HelloWorldReadVo.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<HelloWorldCudVo> deletedByDataId(List<String> dataList) {
        ArrayList<HelloWorld> helloWorlds = new ArrayList<>();
        dataList.forEach(item -> {
            LambdaQueryWrapper<HelloWorld> wrapper = new QueryWrapper<HelloWorld>()
                    .lambda()
                    .eq(HelloWorld::getDataId, item)
                    .eq(BaseModel::getDeleted, Database.DELETED_FALSE);

            HelloWorld one = this.getOne(wrapper);
            if (null == one) {
                throw new BusinessException(ResponseCodeEnum.ENTRY_NOT_EXIST.getCode(),
                        item.concat(ResponseCodeEnum.ENTRY_NOT_EXIST.getMsg()));
            }
            HelloWorld helloWorld = new HelloWorld();
            helloWorld.setDeleted(Database.DELETED_TRUE);
            helloWorld.setDataId(item);
            helloWorlds.add(helloWorld);
            boolean update = this.update(helloWorld, wrapper);
            if (!update) {
                throw new BusinessException(ResponseCodeEnum.BATCH_DELETED_FAILED);
            }
        });

        return BeanUtil.map2List(helloWorlds, HelloWorldCudVo.class);

    }

    @Override
    public List<HelloWorldCudVo> updateHelloWorldByDataId(List<HelloWorldRequestUpdate> updateList) {

        if (updateList.size() > 0) {
            List<HelloWorld> helloWorlds = BeanUtil.map2List(updateList, HelloWorld.class);

            helloWorlds.forEach(item -> {
                String dataId = item.getDataId();
                //根据id速度更快
                if (StringUtils.isNotEmpty(dataId)) {

                    LambdaQueryWrapper<HelloWorld> wrapper = new QueryWrapper<HelloWorld>()
                            .lambda()
                            .eq(HelloWorld::getDataId, item.getDataId())
                            .eq(BaseModel::getDeleted, Database.DELETED_FALSE);
                    HelloWorld one = this.getOne(wrapper);
                    if (null == one) {
                        throw new BusinessException(ResponseCodeEnum.ENTRY_NOT_EXIST.getCode(),
                                dataId.concat(ResponseCodeEnum.ENTRY_NOT_EXIST.getMsg()));
                    }
                    boolean update = this.update(item, wrapper);
                    if (!update) {
                        throw new BusinessException(ResponseCodeEnum.BATCH_UPDATE_FAILED);
                    }
                }
            });
            return BeanUtil.map2List(helloWorlds, HelloWorldCudVo.class);
        }
        return new ArrayList<>();
    }
}
