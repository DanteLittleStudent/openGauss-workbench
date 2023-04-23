package com.gbase.admin.system.plugin.facade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbase.admin.common.core.domain.model.ops.jdbc.JdbcDbClusterInputDto;
import com.gbase.admin.common.core.domain.model.ops.jdbc.JdbcDbClusterVO;
import com.gbase.admin.system.service.ops.IOpsJdbcDbClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhf
 * @date 2023/1/14 22:22
 **/
@Service
public class JdbcDbClusterFacade {
    @Autowired
    private IOpsJdbcDbClusterService opsJdbcDbClusterService;

    public void add(JdbcDbClusterInputDto clusterInput){
        opsJdbcDbClusterService.add(clusterInput);
    }

    public Page<JdbcDbClusterVO> page(String name, Page page) {
        return opsJdbcDbClusterService.page(name, page);
    }

    public List<JdbcDbClusterVO> listAll() {
        return opsJdbcDbClusterService.listAll();
    }
}
