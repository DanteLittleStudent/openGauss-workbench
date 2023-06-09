package com.gbase.admin.common.core.domain.model.ops.jdbc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lhf
 * @date 2023/1/13 16:31
 **/
@Data
public class JdbcDbClusterImportAnalysisVO {
    private Integer total;
    private Integer succNum;
    private Integer failNum;
    private List<JdbcErrorVO> failDetail;

    public static JdbcDbClusterImportAnalysisVO of(List<JdbcDbClusterInputDto> clusterInputDtoList, List<JdbcDbClusterInputDto> errorCluster) {
        JdbcDbClusterImportAnalysisVO jdbcDbClusterImportAnalysisVO = new JdbcDbClusterImportAnalysisVO();
        List<JdbcErrorVO> failDetailList = new ArrayList<>();
        jdbcDbClusterImportAnalysisVO.setTotal(count(clusterInputDtoList));
        jdbcDbClusterImportAnalysisVO.setFailNum(count(errorCluster,failDetailList));
        jdbcDbClusterImportAnalysisVO.setSuccNum(jdbcDbClusterImportAnalysisVO.getTotal()-jdbcDbClusterImportAnalysisVO.getFailNum());
        jdbcDbClusterImportAnalysisVO.setFailDetail(failDetailList);
        return jdbcDbClusterImportAnalysisVO;
    }

    private static Integer count(List<JdbcDbClusterInputDto> errorCluster, List<JdbcErrorVO> failDetailList) {
        Integer errNum = 0;
        for (JdbcDbClusterInputDto inputDto : errorCluster) {
            List<JdbcDbClusterNodeInputDto> nodes = inputDto.getNodes();
            if (CollUtil.isEmpty(nodes) && StrUtil.isNotEmpty(inputDto.getRemark())){
                errNum++;
                failDetailList.add(JdbcErrorVO.of(inputDto.getClusterName(),null,inputDto.getRemark()));
            }

            if (CollUtil.isNotEmpty(nodes)){
                for (JdbcDbClusterNodeInputDto node : nodes) {
                    if (StrUtil.isNotEmpty(node.getRemark())){
                        errNum++;
                        failDetailList.add(JdbcErrorVO.of(inputDto.getClusterName(),node.getUrl(),node.getRemark()));
                    }
                }
            }

        }
        return errNum;
    }

    private static Integer count(List<JdbcDbClusterInputDto> clusterInputDtoList) {
        final Long count = clusterInputDtoList.stream().map(JdbcDbClusterInputDto::getNodes).flatMap(nodeList->nodeList.stream()).collect(Collectors.counting());
        return count.intValue();
    }

    @Data
    static class JdbcErrorVO{
        private String clusterName;
        private String url;
        private String errMsg;

        public static JdbcErrorVO of(String clusterName, String url, String errMsg) {
            JdbcErrorVO jdbcErrorVO = new JdbcErrorVO();
            jdbcErrorVO.setClusterName(clusterName);
            jdbcErrorVO.setUrl(url);
            jdbcErrorVO.setErrMsg(errMsg);
            return jdbcErrorVO;
        }
    }
}
