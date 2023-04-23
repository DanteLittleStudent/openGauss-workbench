package com.gbase.admin.system.service.ops;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.common.core.domain.entity.ops.OpsJdbcDbClusterEntity;
import com.gbase.admin.common.core.domain.model.ops.jdbc.JdbcDbClusterImportAnalysisVO;
import com.gbase.admin.common.core.domain.model.ops.jdbc.JdbcDbClusterInputDto;
import com.gbase.admin.common.core.domain.model.ops.jdbc.JdbcDbClusterVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lhf
 * @date 2023/1/13 11:07
 **/
public interface IOpsJdbcDbClusterService extends IService<OpsJdbcDbClusterEntity> {
    void add(JdbcDbClusterInputDto clusterInput);

    Page<JdbcDbClusterVO> page(String name, Page page);

    void del(String clusterId);

    void update(String clusterId, JdbcDbClusterInputDto clusterInput);

    JdbcDbClusterImportAnalysisVO importAnalysis(MultipartFile file);

    void importCluster(MultipartFile file);

    List<JdbcDbClusterVO> listAll();
}
