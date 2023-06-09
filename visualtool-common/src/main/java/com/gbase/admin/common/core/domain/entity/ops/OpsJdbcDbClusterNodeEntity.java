package com.gbase.admin.common.core.domain.entity.ops;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gbase.admin.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhf
 * @date 2023/1/13 11:02
 **/
@Data
@TableName("ops_jdbcdb_cluster_node")
@EqualsAndHashCode(callSuper = true)
public class OpsJdbcDbClusterNodeEntity extends BaseEntity {
    @TableId
    private String clusterNodeId;
    private String clusterId;
    private String name;
    private String ip;
    private String port;
    private String username;
    private String password;
    private String url;
}
