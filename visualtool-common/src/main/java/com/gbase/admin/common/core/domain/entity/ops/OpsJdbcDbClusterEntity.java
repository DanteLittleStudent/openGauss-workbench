package com.gbase.admin.common.core.domain.entity.ops;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gbase.admin.common.core.domain.BaseEntity;
import com.gbase.admin.common.enums.ops.DbTypeEnum;
import com.gbase.admin.common.enums.ops.DeployTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhf
 * @date 2023/1/13 10:59
 **/
@Data
@TableName("ops_jdbcdb_cluster")
@EqualsAndHashCode(callSuper = true)
public class OpsJdbcDbClusterEntity extends BaseEntity {
    @TableId
    private String clusterId;
    private String name;
    private DbTypeEnum dbType;
    private DeployTypeEnum deployType;
}
