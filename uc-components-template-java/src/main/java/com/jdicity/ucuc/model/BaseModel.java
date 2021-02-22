package com.jdicity.ucuc.model;

import lombok.Data;

import java.util.Date;


/**
 * Database base field.
 *
 * @author liyingda
 * @date 2020-11-20 20:05
 * @version V1.0.0
 */
@Data
public class BaseModel {
    /**
     * created time
     */
    private Date created;

    /**
     * deleted 状态（0未删除，1已删除）
     */
    private Integer deleted;

    /**
     * updated time
     */
    private Date updated;
}
