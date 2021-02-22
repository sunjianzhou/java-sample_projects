package com.example.demoproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 20:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    private String userName;

//    @JsonIgnore     // JsonIgnore是为了在做json序列化的时候不会参与，比如这种密码信息，返回给用户显然是不合适的。
    // 这里加了JsonIgnore之后，连传进来的参数都会呗Ignore掉。。。
    private String pwd;
}
