package io.github.syske.springbootswagger2demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: spring-boot-swagger2-demo
 * @description: 测试bean
 * @author: syske
 * @create: 2020-02-28 16:22
 */
@ApiModel("swaggerDome实体")
public class TestEntity implements Serializable {
    // 名称
    @ApiModelProperty(name = "name", value = "名称：用户名称", required = true, notes = "这个是提示信息")
    private String name;
    // id
    @ApiModelProperty(value = "用户id，默认为010001",
            allowableValues = "010001,020001,030001,040001,050001,060001,070001,080001,090001,100001,110001,120001,990001")

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestEntity{}";
    }
}
