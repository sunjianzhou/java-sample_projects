package com.jdicity.ucuc.schemas.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * Response of hello world read request.
 *
 * @author liyingda
 * @date 2020-11-16 15:30
 * @version V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "hello world response")
@Data
public class HelloWorldReadVo extends HelloWorldCudVo {
    /**
     * Hello World id
     */
    @ApiModelProperty(value = "hello id", required = true)
    private String dataId;

    /**
     * Hello World name
     */
    @ApiModelProperty(value = "hello名称", required = true)
    private String helloWorldName;

    /**
     * Hello World 显示状态（0已隐藏，1显示）
     */
    @ApiModelProperty(value = "hello状态", required = true)
    private Integer status;

    /**
     * Hello World 创建时间
     */
    @ApiModelProperty(value = "hello创建时间", required = true)
    private Date created;

    /**
     * Hello World 更新时间
     */
    @ApiModelProperty(value = "hello更新时间", required = true)
    private Date updated;
}
