package com.zr.looklog.thread;

import com.jcraft.jsch.Session;
import com.zr.looklog.controller.SshController;
import com.zr.looklog.tool.LogTool;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TimeThread {

    private TimeThread() {
    }

    private static TimeThread noticeThread = new TimeThread();

    public static TimeThread getInstance() {
        return noticeThread;
    }


    public void start() {

        Thread thread = new Thread(() -> {
            ConcurrentHashMap<String, Integer> session = SshController.session;
            Set<Map.Entry<String, Integer>> entries = session.entrySet();
            while (Boolean.TRUE) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                entries.forEach(tar -> {
                    if (tar.getValue() > 0) {
                        tar.setValue(tar.getValue() - 1);
                    }
                    if (tar.getValue() <= 0) {
                        session.remove(tar.getKey());
                        ConcurrentHashMap<String, Session> sessionMap = LogTool.sessionMap;
                        Optional.ofNullable(sessionMap.get(tar.getKey())).ifPresent(t -> {
                            t.disconnect();
                            sessionMap.remove(tar.getKey());
                        });
                    }
                });
            }

        });
        thread.setDaemon(true);
        thread.setName("Time-Thread");
        thread.start();
    }

}
