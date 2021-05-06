package io.gitgub.syske.server;

/**
 * @program: http-server
 * @description:
 * @author: syske
 * @create: 2020-10-09 10:14
 */
public class HttpServerApplication {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}