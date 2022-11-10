package com.zr.looklog.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogPo {

    private Long id;

    private String serverName;

    private String serverIp;

    private String serverLogDir;

}
