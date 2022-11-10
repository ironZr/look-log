package com.zr.looklog;

import com.zr.looklog.schedule.JobSchedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LookLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LookLogApplication.class, args);
        new JobSchedule().init();
    }

}
