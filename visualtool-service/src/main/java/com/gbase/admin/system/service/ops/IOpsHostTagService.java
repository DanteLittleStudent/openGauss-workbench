package com.gbase.admin.system.service.ops;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.common.core.domain.entity.ops.OpsHostTagEntity;
import com.gbase.admin.common.core.domain.model.ops.host.tag.HostTagInputDto;
import com.gbase.admin.common.core.domain.model.ops.host.tag.HostTagPageVO;

/**
 * @author lhf
 * @date 2023/3/14 23:36
 **/
public interface IOpsHostTagService extends IService<OpsHostTagEntity> {
    void addTag(HostTagInputDto hostTagInputDto);

    IPage<HostTagPageVO> page(Page page, String name);

    void add(String name);

    void update(String tagId, String name);
}
