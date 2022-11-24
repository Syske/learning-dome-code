/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.sofa.facade.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果基类
 *
 * @author syske
 * @version 1.0
 * @date 2022-11-06 22:45
 */
@Data
public class HelloBaseResult implements Serializable {

    private static final long serialVersionUID = -8342456734245502366L;

    private Long id;
    private String name;
}
