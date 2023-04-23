package com.gbase.admin.system.service.ops;

import com.gbase.admin.common.enums.ops.OpenGaussVersionEnum;

/**
 * Cluster Installation Service Provider Specification
 *
 * @author lhf
 * @date 2022/8/12 09:09
 **/
public interface ClusterOpsProvider {
    /**
     * Installed openGauss version
     *
     * @return openGauss version
     */
    OpenGaussVersionEnum version();
}
