/* Copyright © 2021 syske. All rights reserved. */
package com.example.spingboowebsocketdemo.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * webSoket服务
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-25 13:38
 */
@ServerEndpoint("/ws")
@Service
public class WebSocketService {
    private final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private Map<String, String> nameMap = Maps.newHashMap();

    {
        nameMap.put("nezha", "哪吒");
        nameMap.put("pangu", "盘古");
        nameMap.put("zhongkui", "钟馗");
        nameMap.put("fuxi", "伏羲");
        nameMap.put("shennongshi", "神农氏");
        nameMap.put("kuafu", "夸父");
        nameMap.put("nvwa", "女娲");
        nameMap.put("jiangziya", "姜子牙");
        nameMap.put("jingwei", "精卫");
    }

    // 在线数量
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    private static CopyOnWriteArraySet<WebSocketService> webSocketServiceSet = Sets.newCopyOnWriteArraySet();

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnOpen
    public void onOpen(Session session) {
        String name = nameMap.get(session.getUserPrincipal().getName());
        this.session = session;
        webSocketServiceSet.add(this);
        addOnlineCount();
        logger.info("有新连接加入！当前在线人数为: {}", onlineCount.get());
        webSocketServiceSet.parallelStream().forEach(item -> {
            try {
                sendMessage(item.getSession(), String.format("%s加入群聊！", name));
            } catch (Exception e) {
                logger.error("发送消息异常：", e);
            }
        });
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息：{}", message);
        webSocketServiceSet.parallelStream().forEach(item -> {
            String name = nameMap.get(session.getUserPrincipal().getName());
            logger.info("{}发送了一条消息：{}", name, message);
            try {
                item.sendMessage(item.getSession(), String.format("%s:%s", name, message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose() {
        webSocketServiceSet.remove(this);
        subOnlineCount();
    }

    @OnError
    public void onError(Session session, Throwable t) {
        logger.error("发生错误：", t);
    }

    /**
     * 在线人数加一
     */
    private void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    /**
     * 在线人数减一
     */
    private void subOnlineCount() {
        onlineCount.decrementAndGet();
    }

    private void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
