package io.gitgub.syske.server;

import io.gitgub.syske.server.handler.GetRequestHandler;
import io.gitgub.syske.server.handler.PostRequestHandler;
import io.gitgub.syske.server.handler.RequestHandler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: http-server
 * @description:
 * @author: syske
 * @create: 2020-10-09 10:04
 */
public class RequestHandlerFactory {
    public static final Map<HttpMethod, RequestHandler> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLERS.put(HttpMethod.POST, new PostRequestHandler());
    }

    public static RequestHandler create(HttpMethod httpMethod) {
        return REQUEST_HANDLERS.get(httpMethod);
    }
}
