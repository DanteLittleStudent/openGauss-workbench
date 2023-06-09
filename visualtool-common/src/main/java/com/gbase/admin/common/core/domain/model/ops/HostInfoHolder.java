package com.gbase.admin.common.core.domain.model.ops;

import com.gbase.admin.common.core.domain.entity.ops.OpsHostEntity;
import com.gbase.admin.common.core.domain.entity.ops.OpsHostUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lhf
 * @date 2022/8/13 11:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostInfoHolder {
    private OpsHostEntity hostEntity;
    private List<OpsHostUserEntity> hostUserEntities;
}

