package com.gbase.admin.system.plugin.facade;

import com.gbase.admin.common.core.domain.model.ops.OpsClusterVO;
import com.gbase.admin.system.service.ops.IOpsClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhf
 * @date 2022/10/10 13:35
 **/
@Service
public class OpsFacade {

    @Autowired
    private IOpsClusterService opsClusterService;

    public List<OpsClusterVO> listCluster() {
        return opsClusterService.listCluster();
    }


}
