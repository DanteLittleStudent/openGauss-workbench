package com.gbase.admin.common.core.domain.model.ops;

import com.gbase.admin.common.enums.ops.HostAuthenticationTypeEnum;
import lombok.Data;

/**
 * Host credential
 *
 * @author lhf
 * @date 2022/8/4 22:17
 **/
@Data
public class HostCredentials {

    private String principal;

    private String credentials;

    private HostAuthenticationTypeEnum authenticationType;
}
