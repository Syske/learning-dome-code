package io.github.syske.netty.http_server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class HttpServerChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest > {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest  request) {

        ctx.channel().remoteAddress();


        System.out.println("请求方法名称:" + request.method().name());

        System.out.println("uri:" + request.uri());

        ByteBuf content = request.content();
        String contentString = content.toString(CharsetUtil.UTF_8);
        System.out.println(contentString);

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

        ctx.writeAndFlush(response);
    }
}
