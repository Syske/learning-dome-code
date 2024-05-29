package io.github.syske.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleChatClient {
    public static void main(String[] args) throws Exception{
        new SimpleChatClient("localhost", 8080).run();
    }

    private final String host;
    private final int port;

    public SimpleChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientHandler());
            Channel channel = bootstrap.connect(host, port).sync().channel();
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ByteBuf byteBuf = Unpooled.copiedBuffer("Netty rocks!", //2
                    CharsetUtil.UTF_8);
            channel.writeAndFlush(byteBuf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
