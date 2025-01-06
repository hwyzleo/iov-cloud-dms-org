package net.hwyz.iov.cloud.dms.org.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao.DealershipDao;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao.DealershipStaffDao;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipStaffPo;
import net.hwyz.iov.cloud.framework.common.bean.TreeSelect;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.mpt.system.api.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 销售门店员工应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DealershipStaffAppService {

    private final DealershipDao dealershipDao;
    private final DealershipStaffDao dealershipStaffDao;

    /**
     * 查询门店员工信息
     *
     * @param regionCode     大区代码
     * @param areaCode       小区代码
     * @param dealershipCode 门店代码
     * @param dealershipName 门店名称
     * @param userName       用户账号
     * @param phonenumber    手机号
     * @param beginTime      开始时间
     * @param endTime        结束时间
     * @return 车辆平台列表
     */
    public List<DealershipStaffPo> search(String regionCode, String areaCode, String dealershipCode, String dealershipName,
                                          String userName, String phonenumber, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isNotBlank(regionCode)) {
            List<DealershipPo> regionDealershipList = dealershipDao.selectPoByExample(DealershipPo.builder().regionCode(regionCode).build());
            List<String> regionDealershipCodeList = new java.util.ArrayList<>(regionDealershipList.stream().map(DealershipPo::getCode).toList());
            if (regionDealershipCodeList.isEmpty()) {
                regionDealershipCodeList.add("");
            }
            map.put("dealershipList", regionDealershipCodeList);
        }
        if (StrUtil.isNotBlank(areaCode)) {
            List<DealershipPo> areaDealershipList = dealershipDao.selectPoByExample(DealershipPo.builder().areaCode(areaCode).build());
            List<String> areaDealershipCodeList = new java.util.ArrayList<>(areaDealershipList.stream().map(DealershipPo::getCode).toList());
            if (areaDealershipCodeList.isEmpty()) {
                areaDealershipCodeList.add("");
            }
            map.put("dealershipList", areaDealershipCodeList);
        }
        map.put("dealershipCode", dealershipCode);
        map.put("dealershipName", ParamHelper.fuzzyQueryParam(dealershipName));
        map.put("userName", ParamHelper.fuzzyQueryParam(userName));
        map.put("phonenumber", ParamHelper.fuzzyQueryParam(phonenumber));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return dealershipStaffDao.selectPoByMap(map);
    }

    /**
     * 根据关键字查询用户
     *
     * @param key 关键字
     * @return 用户列表
     */
    public List<TreeSelect> searchUser(String key) {
        List<SysUser> sysUsers = dealershipStaffDao.selectSysUserByKey(ParamHelper.fuzzyQueryParam(key));
        return sysUsers.stream()
                .map(sysUser -> {
                    TreeSelect treeSelect = new TreeSelect();
                    treeSelect.setId(sysUser.getUserId().toString());
                    treeSelect.setLabel(sysUser.getUserName() + ", " + sysUser.getNickName() + ", " + sysUser.getPhonenumber());
                    treeSelect.setType(sysUser.getNickName());
                    return treeSelect;
                })
                .collect(Collectors.toList());
    }

    /**
     * 检查用户ID是否唯一
     *
     * @param dealershipStaffId 门店员工ID
     * @param userId            用户ID
     * @return 结果
     */
    public Boolean checkUserIdUnique(Long dealershipStaffId, Long userId) {
        if (ObjUtil.isNull(dealershipStaffId)) {
            dealershipStaffId = -1L;
        }
        DealershipStaffPo dealershipStaffPo = getDealershipStaffByUserId(userId);
        return !ObjUtil.isNotNull(dealershipStaffPo) || dealershipStaffPo.getId().longValue() == dealershipStaffId.longValue();
    }

    /**
     * 根据主键ID获取门店员工
     *
     * @param id 主键ID
     * @return 门店员工
     */
    public DealershipStaffPo getDealershipStaffById(Long id) {
        return dealershipStaffDao.selectPoById(id);
    }

    /**
     * 根据用户ID获取门店员工
     *
     * @param userId 用户ID
     * @return 门店员工
     */
    public DealershipStaffPo getDealershipStaffByUserId(Long userId) {
        return dealershipStaffDao.selectPoByUserId(userId);
    }

    /**
     * 新增门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 结果
     */
    public int createDealershipStaff(DealershipStaffPo dealershipStaff) {
        return dealershipStaffDao.insertPo(dealershipStaff);
    }

    /**
     * 修改门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 结果
     */
    public int modifyDealershipStaff(DealershipStaffPo dealershipStaff) {
        return dealershipStaffDao.updatePo(dealershipStaff);
    }

    /**
     * 批量删除门店员工
     *
     * @param ids 门店员工ID数组
     * @return 结果
     */
    public int deleteDealershipStaffByIds(Long[] ids) {
        return dealershipStaffDao.batchPhysicalDeletePo(ids);
    }

}
