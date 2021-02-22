package com.example.demoproject.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/25 9:28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonData {
    private int code;

    private Object data;

    private String msg;

    public JsonData(int code, Object data){
        this.code = code;
        this.data = data;
    }

    public static JsonData buildSuccess(Object data){
        return new JsonData(0, data);
    }

    public static JsonData buildError(String msg){
        return new JsonData(-1, msg);
    }

    public static JsonData buildError(int code, String msg){
        return new JsonData(code, msg);
    }
}
