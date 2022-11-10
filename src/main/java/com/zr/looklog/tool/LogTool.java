package com.zr.looklog.tool;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zr.looklog.po.LogPo;
import com.zr.looklog.vo.MessageVo;
import com.zr.looklog.websocket.WebSocketHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LogTool {


    private static JdbcTemplate jdbcTemplate;


    public static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();


    @Resource
    public void setChatService(JdbcTemplate jdbcTemplate) {
        LogTool.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 读取日志
     *
     * @param id id
     * @throws JSchException
     * @throws IOException
     */
    public static void getLog(String token, Integer id) throws JSchException, IOException {

        LogPo logPo = jdbcTemplate.queryForObject("select * from log_server_properties where id=?", new BeanPropertyRowMapper<>(LogPo.class), id);

        Session session = JschUtil.createSession(logPo.getServerIp(), 22, "zhangrui", "admin@123");

        sessionMap.put(token, session);

        session.connect(30000);
        String command = String.format("tail -n 200 -f %s \r", logPo.getServerLogDir());
        Channel channel = session.openChannel("shell");
        channel.connect(3000);

        OutputStream outputStream = channel.getOutputStream();
        outputStream.write(command.getBytes());
        outputStream.flush();

        InputStream inputStream = channel.getInputStream();

        String tar = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {

            while ((tar = reader.readLine()) != null) {
                tar = tar.replaceAll("&", "&amp;")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
                        .replaceAll("\"", "&quot;");
                //处理等级
                tar = tar.replace("DEBUG", "<span style='color: blue;'>DEBUG</span>");
                tar = tar.replace("INFO", "<span style='color: green;'>INFO</span>");
                tar = tar.replace("WARN", "<span style='color: orange;'>WARN</span>");
                tar = tar.replace("ERROR", "<b><span style='color: red;'>ERROR</span></b>");
                MessageVo messageVo = MessageVo.builder().message(tar).type(1).build();
                WebSocketHandler.sendMessageToUser(token, messageVo);
            }
        } finally {
            session.disconnect();
            channel.disconnect();
            if (ObjectUtil.isNotEmpty(inputStream)) {
                inputStream.close();
            }
        }

    }


}
