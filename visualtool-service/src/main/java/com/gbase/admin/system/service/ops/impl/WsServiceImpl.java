package com.gbase.admin.system.service.ops.impl;

import com.jcraft.jsch.ChannelShell;
import lombok.extern.slf4j.Slf4j;
import com.gbase.admin.common.core.domain.model.ops.WsSession;
import com.gbase.admin.common.core.handler.ops.cache.SSHChannelManager;
import com.gbase.admin.common.core.handler.ops.cache.TaskManager;
import com.gbase.admin.common.core.handler.ops.cache.WsConnectorManager;
import com.gbase.admin.common.exception.ops.OpsException;
import com.gbase.admin.common.utils.ops.JschUtil;
import com.gbase.admin.system.service.ops.IWsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lhf
 * @date 2022/8/6 18:14
 **/
@Slf4j
@Service
public class WsServiceImpl implements IWsService {
    @Autowired
    private JschUtil jschUtil;
    @Autowired
    private WsConnectorManager wsConnectorManager;

    @Override
    public void onOpen(String businessId, Session session) {
        wsConnectorManager.register(businessId, new WsSession(session, businessId));
    }

    @Override
    public void onClose(String businessId) {
        wsConnectorManager.getSession(businessId).ifPresent(wsSession -> {
            try {
                wsSession.getSession().close();
            } catch (IOException e) {
                log.error("websocket session close fail", e);
            }
        });
        TaskManager.remove(businessId).ifPresent(future -> future.cancel(true));
        wsConnectorManager.remove(businessId);
    }

    @Override
    public void onMessage(String businessId, String message) {
        ChannelShell channelShell = SSHChannelManager.getChannelShell(businessId).orElseThrow(() -> new OpsException("No connection information found"));
        jschUtil.sendToChannelShell(channelShell, message);
    }

    @Override
    public void sendMessage(Session session, byte[] message) {
        try {
            session.getBasicRemote().sendText(new String(message));
        } catch (IOException e) {
            log.error("Failed to send ws message", e);
        }
    }

    @Override
    public WsSession getRetWsSession(String businessId) {
        return wsConnectorManager
                .getSession(businessId)
                .orElseThrow(() -> new OpsException("response session does not exist"));
    }

    @Override
    public Optional<Session> obtainFreshSession(WsSession wsSession) {
        if (wsSession.getSession().isOpen()){
            return Optional.ofNullable(wsSession.getSession());
        }

        AtomicReference<Session> session = new AtomicReference<>();
        wsConnectorManager.getSession(wsSession.getBusinessId()).ifPresent(wsSession1 -> session.set(wsSession1.getSession()));
        return Optional.ofNullable(session.get());
    }
}
