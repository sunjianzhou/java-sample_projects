package com.jdicity.ucuc.schemas.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * hello world更新参数.
 *
 * @author liyingda
 * @date 2020-11-19 17:38
 * @version V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloWorldRequestUpdate extends HelloWorldRequestCreate {

    /**
     * Hello World dataId.
     */
    @ApiModelProperty(value = "hello world dataId")
    private String dataId;

}
