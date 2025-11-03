package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    public static <T> Result<T> success(T data) {return new Result<>(0, "操作成功", data);}
    public static <T> Result<T> success() {return new Result<>(0, "操作成功", null);}
    public static <T> Result<T> error(String message) {return new Result<>(1, message, null);}

    public Result<T> data(String key, Object value) {
        if (this.data == null){
            this.data = (T) new HashMap<>();
        }
        ((Map<String, Object>) this.data).put(key, value);
        return this;
    }
}
