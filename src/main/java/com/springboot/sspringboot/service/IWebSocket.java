package com.springboot.sspringboot.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
@Slf4j
public class IWebSocket {
    private Session session;

    private static CopyOnWriteArraySet<IWebSocket>  webSockets = new CopyOnWriteArraySet<>();
    @OnOpen
    public void opOpen(Session session){
        this.session=session;
        webSockets.add(this);
        log.info("【websocket消息】有新的消息连接，总数：{}",webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("【websocket消息】 连接断开，总数={}",webSockets.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端消息：{}",message);
    }

    public  void sendMessage(String message){
        for(IWebSocket iWebSocket:webSockets){
            try {
                log.info("【websocket消息】广播消息，message={}",message);
                iWebSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
