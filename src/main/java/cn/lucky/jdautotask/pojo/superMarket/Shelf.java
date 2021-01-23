package cn.lucky.jdautotask.pojo.superMarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 货架信息
 */
@NoArgsConstructor
@Data
public class Shelf {


    /**
     * category : 1
     * id : shelf-1
     * level : 10
     * name : 普通货架
     * status : 0
     * turnoverSpeed : 12
     */

    @JsonProperty("category")
    private Integer category;
    @JsonProperty("id")
    private String id;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("turnoverSpeed")
    private Integer turnoverSpeed;
}
