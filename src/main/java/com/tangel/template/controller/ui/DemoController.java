package com.tangel.template.controller.ui;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模版项目
 * @author create by luotj
 * @Date: 2020/6/6 6:57 下午
 **/
@Api(tags = "01. 项目模版")
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @ApiOperation("模版接口")
    @GetMapping("/template")
    public String templateProject(){
        return "hello , i am template project!!!";
    }

}
