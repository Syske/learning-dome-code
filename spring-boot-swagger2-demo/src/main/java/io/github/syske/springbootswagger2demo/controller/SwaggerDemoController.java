package io.github.syske.springbootswagger2demo.controller;

import io.github.syske.springbootswagger2demo.entity.TestEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: spring-boot-swagger2-demo
 * @description: swagger controller示例
 * @author: syske
 * @create: 2020-02-28 16:18
 */
@Controller
@Api(description = "spring boot swagger demo22222")
@RequestMapping("/swagger/dome")
public class SwaggerDemoController {

    @ResponseBody
    @ApiOperation(value = "swagger2示例接口描述",httpMethod = "POST",
            notes = "这里是notes信息", response = TestEntity.class)
    @RequestMapping("/list")
    public TestEntity listTest(TestEntity testEntity) {
        return testEntity;
    }


    @ResponseBody
    @ApiOperation(value = "swagger2示例接口描述2",httpMethod = "GET",
            notes = "这里是notes信息2")
    @RequestMapping("/list2")
    public String listTest2(@ApiParam(name = "name", value = "value",
            allowableValues = "test,test2",example = "小王", required = true) String name) {
        return name + ", hello";
    }
}
