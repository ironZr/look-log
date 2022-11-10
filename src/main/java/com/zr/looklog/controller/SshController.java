package com.zr.looklog.controller;

import cn.hutool.core.lang.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/ssh")
public class SshController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public static ConcurrentHashMap<String, Integer> session = new ConcurrentHashMap();

    @GetMapping("/login")
    public ResponseEntity<String> login(Integer id) {
        String uuid = UUID.randomUUID().toString(true);
        session.put(uuid, 15 * 60);
        return ResponseEntity.ok(uuid);
    }

    @GetMapping("/listServer")
    public ResponseEntity<List<Map<String, Object>>> listServer() {
        return ResponseEntity.ok(jdbcTemplate.queryForList("select * from log_server_properties"));
    }

    @GetMapping("/close/{token}")
    public void close(@PathVariable(value = "token") String token) {
        session.put(token, 5);
    }


}
