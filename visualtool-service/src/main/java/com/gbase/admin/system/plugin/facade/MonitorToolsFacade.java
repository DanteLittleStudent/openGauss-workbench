package com.gbase.admin.system.plugin.facade;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.extern.slf4j.Slf4j;
import com.gbase.admin.system.plugin.beans.MonitorToolsPromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MonitorToolsFacade {

    @Autowired
    private CollectorRegistry collectorRegistry;

    private Gauge my_library_transactions_active;

    private List<MonitorToolsPromDto> list = new ArrayList<>();

    public void reportRegister(String metricKey, Object metricValue, String[] key, String[] value) {
        log.info("monitorTools report");
        MonitorToolsPromDto prom = list.stream().filter(item -> item.getGaugeName().equals(metricKey)).findFirst().orElse(null);
        if (null != prom) {
            my_library_transactions_active = prom.getGauge();
        } else {
            my_library_transactions_active = Gauge.build()
                    .name(metricKey)
                    .help("Active transactions.")
                    .labelNames(key)
                    .register(collectorRegistry);
            MonitorToolsPromDto promNew = new MonitorToolsPromDto(my_library_transactions_active, metricKey);
            list.add(promNew);
        }
        String num = new BigDecimal(metricValue.toString()).toPlainString();
        my_library_transactions_active.labels(value).set(Double.valueOf(num));
    }

    public void removeRegister(List<String> gaugeName) {
        log.info("monitorTools remove");
        try {
            Thread.sleep(6000);
            if (gaugeName != null && gaugeName.size() > 0) {
                for (String str : gaugeName) {
                    MonitorToolsPromDto prom = list.stream().filter(item -> str.equals(item.getGaugeName())).findFirst().orElse(null);
                    if (prom != null) {
                        list.remove(prom);
                        collectorRegistry.unregister(prom.getGauge());
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("removeAll-->{}", e.getMessage());
        }
    }
}
