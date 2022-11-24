/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.sofa.facade.model.result;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author syske
 * @version 1.0
 * @date 2022-11-06 22:45
 */
@Data
@ToString(callSuper = true)
public class HelloDataResult extends HelloBaseResult implements Serializable {
    private static final long serialVersionUID = -3426180359345412732L;

    private Long id;
}
