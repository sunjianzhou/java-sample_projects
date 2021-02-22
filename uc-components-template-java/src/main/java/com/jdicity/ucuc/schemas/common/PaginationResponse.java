package com.jdicity.ucuc.schemas.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Universal response of pagination.
 *
 * @author liyingda
 * @date 2020-11-19 18:12
 * @param <T>
 * @version v1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    /**
     * pageSize
     */
    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    /**
     * pageNum
     */
    @ApiModelProperty(value = "当前页")
    private Integer page;

    /**
     * total num of records
     */
    @ApiModelProperty(value = "总条数")
    private Integer total;

    /**
     * response data
     */
    @ApiModelProperty(value = "数据集合")
    private List<T> list;
}
