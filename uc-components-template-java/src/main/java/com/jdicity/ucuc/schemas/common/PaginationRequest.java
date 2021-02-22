package com.jdicity.ucuc.schemas.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;


/**
 * Base schema of pagination.
 *
 * @author liyingda
 * @date 2020-11-19 18:12
 * @version v1.0.0
 */
@ApiModel(description = "分页父类")
@Data
public class PaginationRequest {
    /**
     * pageSize
     */
    @ApiModelProperty(value = "每页显示条数")
    @Min(value = 1, message = "分页数异常")
    private Integer pageSize = 10;

    /**
     * pageNum
     */
    @ApiModelProperty(value = "当前页")
    @Min(value = 1, message = "页码异常")
    private Integer page = 1;

    /**
     * sort
     */
    @ApiModelProperty(value = "排序字段")
    private List<String> sort;
}
