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
    @ApiModelProperty(name = "name",value = "名称：用户名称", required =true, notes = "这个是提示信息")
    private String name;
    // id
    @ApiModelProperty(value = "就医地编码，默认为61990001",
            allowableValues = "61010001,61020001,61030001,61040001,61050001,61060001,61070001,61080001,61090001,61100001,61110001,61120001,61990001")

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
