package com.jdicity.ucuc.schemas.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdicity.ucuc.schemas.common.PaginationRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.validation.constraints.NotBlank;


/**
 * Hello world request schema of read.
 *
 * @author liyingda
 * @date 2020-11-19 17:38
 * @version V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "接收参数：查询 hello world.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloWorldRequestRead extends PaginationRequest {
    /**
     * Hello World id.
     */
    @ApiModelProperty(value = "hello world id", required = true)
    @NotBlank(message = "hello id不能为空.")
    private List<String> dataIds;
    
    /**
     * Hello World name.
     */
    @ApiModelProperty(value = "hello名称", required = true)
    @NotBlank(message = "hello名称不能为空.")
    private String name;

    /**
     * Hello World 显示状态（0已隐藏，1显示）.
     */
    @ApiModelProperty(value = "hello状态")
    private Integer status;
}
