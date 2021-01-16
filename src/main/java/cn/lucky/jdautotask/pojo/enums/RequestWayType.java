package cn.lucky.jdautotask.pojo.enums;

/*
 * @Author zyl
 * @Description 请求方式
 * @Date 2021/1/11 10:59
 **/
public enum RequestWayType {

    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE")

    ;

    private String requestWay;

    RequestWayType(String requestWay){
        this.requestWay = requestWay;
    }

    public String value() {
        return requestWay;
    }
}
