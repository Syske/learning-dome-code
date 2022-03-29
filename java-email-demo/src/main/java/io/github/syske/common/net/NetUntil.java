/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * 网络工具类
 *
 * @author syske
 * @version 1.0
 * @date 2022-03-29 22:10
 */
public class NetUntil {
    private static final Logger logger = Logger.getLogger(NetUntil.class.getName());

    public static boolean telnet(String host, Integer port, Integer timeout) {
        try {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, timeout);
            if (socket.isConnected()) {
                return Boolean.TRUE;
            }
        } catch (IOException e) {
            logger.warning("telnet error" + e);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(telnet("baidu.com", 81, 200));
    }
}
