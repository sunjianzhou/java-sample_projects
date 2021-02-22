package com.jdicity.ucuc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Hello World model of database.
 *
 * @author liyingda
 * @date 2020-11-13 16:30
 * @version V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hello_world")
public class HelloWorld extends BaseModel implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Hello World id
     */
    private String dataId;

    /**
     * Hello World name
     */
    private String helloWorldName;

    /**
     * Hello World 显示状态（0已隐藏，1显示）
     */
    private Integer status;

    /**
     * 租户id
     */
    private Integer tenantId;
}
