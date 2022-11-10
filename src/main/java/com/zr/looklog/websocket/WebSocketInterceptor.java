package com.zr.looklog.websocket;

import cn.hutool.core.util.StrUtil;
import com.zr.looklog.constant.DicConstant;
import com.zr.looklog.controller.SshController;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
                                   Map<String, Object> attr) {
        String uri = request.getURI().toString();

        String parm = StrUtil.subAfter(uri, "?", true);
        if (parm.length() == uri.length()) {
            return Boolean.FALSE;
        }
        HashMap<String, String> map = new HashMap<>();
        for (String s : parm.split("&")) {
            String[] parmArr = s.split("=");
            if (parmArr.length != 2) {
                return Boolean.FALSE;
            }
            map.put(parmArr[0], parmArr[1]);
        }
        String token = map.get(DicConstant.LOGIN_TOKEN);
        if (Boolean.FALSE.equals(SshController.session.containsKey(token))) {
            return Boolean.FALSE;
        }

        attr.put(DicConstant.LOGIN_TOKEN, token);
        attr.put(DicConstant.LOG_ID, map.get(DicConstant.LOG_ID));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
    }
}