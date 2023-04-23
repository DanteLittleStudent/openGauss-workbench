package com.gbase.admin.common.core.domain.model.ops.check;

import lombok.Data;

/**
 * @author lhf
 * @date 2022/10/21 18:35
 **/
@Data
public class CheckNetworkVO {
    private CheckItemVO checkMTU;
    private CheckItemVO checkPing;
    private CheckItemVO checkRXTX;
    private CheckItemVO checkNetWorkDrop;
    private CheckItemVO checkMultiQueue;
    private CheckItemVO checkRouting;
    private CheckItemVO checkNICModel;
}
