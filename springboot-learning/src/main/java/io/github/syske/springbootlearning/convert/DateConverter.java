/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 *
 * @author syske
 * @version 1.0
 * @date 2021-09-16 21:28
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        if(!StringUtils.isEmpty(source)) {
            try {
                date = dateFormat.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}