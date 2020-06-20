package com.tangel.rabbitmq.provider.simple;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author create by luotj
 * @Date: 2020/6/20 5:23 下午
 **/
@ApiModel
@Data
public class SimpleResponse {

    @ApiModelProperty(value = "用户ID", position = 1, example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户名称", position = 2, example = "tangel")
    private String userName;


}
