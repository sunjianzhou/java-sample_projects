package com.jdicity.ucuc.schemas.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Response of hello world CUD request.
 *
 * @author liyingda
 * @date 2020-11-16 15:30
 * @version V1.0.0
 */
@ApiModel(description = "hello world response")
@Data
public class HelloWorldCudVo implements Serializable {
    /**
     * Hello World id
     */
    @ApiModelProperty(value = "hello id", required = true)
    private String dataId;
}
