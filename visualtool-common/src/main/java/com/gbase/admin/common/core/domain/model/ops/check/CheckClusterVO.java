package com.gbase.admin.common.core.domain.model.ops.check;

import lombok.Data;

/**
 * @author lhf
 * @date 2022/10/21 18:35
 **/
@Data
public class CheckClusterVO {
    private CheckItemVO checkClusterState;
    private CheckItemVO checkDBParams;
    private CheckItemVO checkDebugSwitch;
    private CheckItemVO checkDirPermissions;
    private CheckItemVO checkEnvProfile;
    private CheckItemVO checkReadonlyMode;
    private CheckItemVO checkDilateSysTab;
    private CheckItemVO checkProStartTime;
    private CheckItemVO checkMpprcFile;
}
