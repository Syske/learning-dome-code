package io.gitgub.syske.server.handler;

import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest);
}
