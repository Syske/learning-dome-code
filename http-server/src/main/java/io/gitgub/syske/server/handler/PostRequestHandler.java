package io.gitgub.syske.server.handler;

import io.gitgub.syske.server.handler.RequestHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: http-server
 * @description:
 * @author: syske
 * @create: 2020-10-09 10:10
 */
public class PostRequestHandler implements RequestHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        log.info("request uri :[{}]", requestUri);
        String contentType = this.getContentType(fullHttpRequest.headers());
        if (contentType.equals("application/json")) {
            return fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }

    }

    private String getContentType(HttpHeaders headers) {
        String typeStr = headers.get("Content-Type");
        String[] list = typeStr.split(";");
        return list[0];
    }
}
