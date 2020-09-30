package io.igthub.syske.springbootstrategypattern.service.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessType {
    String value();
}
