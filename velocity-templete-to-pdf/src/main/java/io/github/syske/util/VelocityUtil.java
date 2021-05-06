package io.github.syske.util;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @program: velocity-templete-to-pdf
 * @description:
 * @author: syske
 * @create: 2020-06-30 17:02
 */


/**
 * 模板数据
 **/
public class VelocityUtil {

    public static String evaluateString(String template,
                                        Map<String, Object> data) throws Exception {
        VelocityContext context = new VelocityContext();
        data.entrySet().forEach(entry -> {
            context.put(entry.getKey(), entry.getValue());
        });
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "template", template);
        return writer.toString();
    }
}