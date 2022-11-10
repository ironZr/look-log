package com.zr.looklog.thread;

import com.zr.looklog.controller.SshController;
import com.zr.looklog.vo.MessageVo;
import com.zr.looklog.websocket.WebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NoticeThread {

    private NoticeThread() {
    }

    private static NoticeThread noticeThread = new NoticeThread();

    public static NoticeThread getInstance() {
        return noticeThread;
    }


    public void start() {
        Thread thread = new Thread(() -> {
            Set<Map.Entry<String, Integer>> entries = SshController.session.entrySet();
            while (Boolean.TRUE) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Map.Entry<String, Integer> entry : entries) {
                    if (entry.getValue() == 60) {
                            WebSocketHandler.sendMessageToUser(entry.getKey(), MessageVo.builder().type(2).message("<b><span style='color: red;'>一分钟后会话过期！</span></b>").build());
                    }

                    if (entry.getValue() == 0) {
                        WebSocketHandler.sendMessageToUser(entry.getKey(), MessageVo.builder().type(2).message("<b><span style='color: red;'>告辞 !!!</span></b>").build());
                    }

                }

            }
        });
        thread.setDaemon(true);
        thread.setName("Notice-Thread");
        thread.start();
    }

}
