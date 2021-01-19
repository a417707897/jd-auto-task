package cn.lucky.jdautotask.handle.common;


import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 结果返回实体
 * @Date 2021/1/19 10:43
 **/
@Data
public class Result {


    public Result(){
        data= new HashMap<>();
    }

    //请求码，并非状态码
    private Integer code;

    //请求结果详情
    private String message;

    //请求实体
    private Map<String, Object> data;

    /*
     * @Author zyl
     * @Description 请求成功
     * @Date 2021/1/19 10:51
     * @Param []
     * @return cn.lucky.jdautotask.handle.common.Result
     **/
    public static Result success(){
        Result result = new Result();
        result.setCode(0);
        return result;
    }

    /*
     * @Author zyl
     * @Description 请求成功
     * @Date 2021/1/19 10:51
     * @Param [message]
     * @return cn.lucky.jdautotask.handle.common.Result
     **/
    public static Result success(@NonNull String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode(0);
        return result;
    }

    /*
     * @Author zyl
     * @Description 请求失败
     * @Date 2021/1/19 10:51
     * @Param [message]
     * @return cn.lucky.jdautotask.handle.common.Result
     **/
    public static Result fail(@NonNull String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode(-1);
        return result;
    }


    public Result add(@NonNull String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Result set(@NonNull Map<String, Object> map) {
        this.set(map);
        return this;
    }
}
