package io.github.syske.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    private SocketChannel channel;
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();                //1
            b.group(group)                                //2
                    .channel(NioSocketChannel.class)            //3
                    .remoteAddress(new InetSocketAddress(host, port))    //4
                    .handler(new ChannelInitializer<SocketChannel>() {    //5
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoClientHandler());
                        }

                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            super.userEventTriggered(ctx, evt);
                        }
                    });
            ChannelFuture f = b.connect().sync();        //6
            f.channel().closeFuture().sync();            //7
        } finally {
            group.shutdownGracefully().sync();            //8
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(
                    "Usage: " + EchoClient.class.getSimpleName() +
                            " <host> <port>");
            return;
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        EchoClient echoClient = new EchoClient(host, port);
        echoClient.start();
    }
}