package com.zr.looklog.schedule;

import com.zr.looklog.thread.NoticeThread;
import com.zr.looklog.thread.TimeThread;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobSchedule {


    public void init() {
        NoticeThread.getInstance().start();

        TimeThread.getInstance().start();

        log.info("[-------------------JobSchedule启动成功------------------------]");
    }
}
