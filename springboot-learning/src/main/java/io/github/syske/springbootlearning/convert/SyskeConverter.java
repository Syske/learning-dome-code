/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.convert;

import io.github.syske.springbootlearning.entity.Result;
import io.github.syske.springbootlearning.entity.ResultVO;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

/**
 * @author syske
 * @version 1.0
 * @date 2021-09-16 8:22
 */
public class SyskeConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Result.class, ResultVO.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }
}
