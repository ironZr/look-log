package com.zr.looklog.websocket;

import cn.hutool.json.JSONUtil;
import com.zr.looklog.constant.DicConstant;
import com.zr.looklog.tool.LogTool;
import com.zr.looklog.vo.MessageVo;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    private static ConcurrentHashMap<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String token = attributes.get(DicConstant.LOGIN_TOKEN).toString();
        Integer id = Integer.valueOf(attributes.get(DicConstant.LOG_ID).toString());

        webSocketSessionMap.put(token, session);

        session.sendMessage(new TextMessage("哒咩 哒咩 哒咩 ~~~"));

        LogTool.getLog(token, id);
        super.afterConnectionEstablished(session);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }


    /**
     * 给某个用户发送消息
     *
     * @param token token
     * @param messageVo messageVo
     */
    public static synchronized void sendMessageToUser(String token, MessageVo messageVo){
        WebSocketSession webSocketSession = webSocketSessionMap.get(token);
        Optional.ofNullable(webSocketSession).ifPresent(tar -> {
            try {
                String messageJson = JSONUtil.toJsonStr(messageVo);
                tar.sendMessage(new TextMessage(messageJson));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
