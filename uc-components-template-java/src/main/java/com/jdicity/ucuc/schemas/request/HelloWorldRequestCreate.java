package com.jdicity.ucuc.schemas.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 新增hello world的参数.
 *
 * @author liyingda
 * @date 2020-11-16 15:30
 * @version V1.0.0
 */
@ApiModel(description = "接收参数：新增 hello world.")
@Data
public class HelloWorldRequestCreate {

    /**
     * Hello World name.
     */
    @ApiModelProperty(value = "hello名称", required = true)
    @NotBlank(message = "hello名称不能为空.")
    private String helloWorldName;

    /**
     * Hello World 显示状态（0已隐藏，1显示）.
     */
    @ApiModelProperty(value = "hello状态")
    private Integer status;
}
