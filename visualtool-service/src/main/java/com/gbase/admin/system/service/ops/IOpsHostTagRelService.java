package com.gbase.admin.system.service.ops;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.common.core.domain.entity.ops.OpsHostTagEntity;
import com.gbase.admin.common.core.domain.entity.ops.OpsHostTagRel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lhf
 * @date 2023/3/14 23:38
 **/
public interface IOpsHostTagRelService extends IService<OpsHostTagRel> {
    void addHostTagRel(List<String> hostIds, List<OpsHostTagEntity> tags);

    Map<String, Set<String>> mapByHostIds(List<String> hostIds);

    void cleanHostTag(String hostId);
}
