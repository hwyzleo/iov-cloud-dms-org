package net.hwyz.iov.cloud.dms.org.service.application.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.Dealership;
import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.assembler.DealershipPoAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao.OrgDao;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.OrgPo;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 组织结构应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrgAppService {

    private final OrgDao orgDao;

    /**
     * 查询组织结构
     *
     * @param code      车辆平台代码
     * @param name      车辆平台名称
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<OrgPo> search(String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return orgDao.selectPoByMap(map);
    }

    /**
     * 检查组织结构代码是否唯一
     *
     * @param orgId 组织结构ID
     * @param code  组织结构代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long orgId, String code) {
        if (ObjUtil.isNull(orgId)) {
            orgId = -1L;
        }
        OrgPo orgPo = getOrgByCode(code);
        return !ObjUtil.isNotNull(orgPo) || orgPo.getId().longValue() == orgId.longValue();
    }

    /**
     * 根据组织结构ID获取组织结构
     *
     * @param id 主键ID
     * @return 组织结构
     */
    public OrgPo getOrgById(Long id) {
        return orgDao.selectPoById(id);
    }

    /**
     * 根据组织结构代码获取组织结构
     *
     * @param code 组织结构代码
     * @return 组织结构
     */
    public OrgPo getOrgByCode(String code) {
        return orgDao.selectPoByCode(code);
    }

    /**
     * 新增组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    public int createOrg(OrgPo org) {
        if (org.getParentId() > 0) {
            OrgPo parentOrg = getOrgById(org.getParentId());
            org.setAncestors(parentOrg.getAncestors() + "," + org.getParentId());
        }
        return orgDao.insertPo(org);
    }

    /**
     * 修改组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    public int modifyOrg(OrgPo org) {
        if (org.getParentId() > 0) {
            OrgPo parentOrg = getOrgById(org.getParentId());
            org.setAncestors(parentOrg.getAncestors() + "," + org.getParentId());
        }
        return orgDao.updatePo(org);
    }

    /**
     * 批量删除组织结构
     *
     * @param ids 组织结构ID数组
     * @return 结果
     */
    public int deleteOrgByIds(Long[] ids) {
        return orgDao.batchPhysicalDeletePo(ids);
    }

}
