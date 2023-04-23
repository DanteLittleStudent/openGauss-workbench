package com.gbase.admin.common.core.domain.entity.ops;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gbase.admin.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhf
 * @date 2023/3/14 23:33
 **/
@Data
@TableName("ops_host_tag")
@EqualsAndHashCode(callSuper = true)
public class OpsHostTagEntity extends BaseEntity {
    @TableId
    private String hostTagId;
    private String name;
}
