package com.gbase.admin.common.core.domain.model.ops;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author lhf
 * @date 2022/8/9 10:00
 **/
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsSession {
    private Session session;
    private String businessId;

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            log.error("Failed to close the ws connection. Procedure", e);
        }
    }
}
