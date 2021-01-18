package cn.lucky.jdautotask.pojo.plantBeanIndex;

import lombok.Data;

/*
 * @Author zyl
 * @Description 商品列表
 * @Date 2021/1/18 11:42
 **/
@Data
public class ProductInfo {


    /**
     * productTaskId : 27643073_100009883451
     * productName : 科尔沁 国产 内蒙古牛肉 炖牛肉1kg 大块谷饲牛肉 冷冻生鲜
     * productImg : https://m.360buyimg.com/babel/jfs/t1/149384/30/19802/195386/5fe45955E1b914e2d/86a0b32164147968.jpg
     * price : 139
     * skuId : 100009883451
     * linkUrl : https://item.m.jd.com/product/100009883451.html
     * taskState : 2
     */

    private String productTaskId;
    private String productName;
    private String productImg;
    private String price;
    private String skuId;
    private String linkUrl;
    private String taskState;
}
