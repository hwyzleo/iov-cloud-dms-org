package net.hwyz.iov.cloud.dms.org.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffMpt;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;

/**
 * 门店员工相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface DealershipStaffMptApi {

    /**
     * 分页查询门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 门店员工列表
     */
    TableDataInfo list(DealershipStaffMpt dealershipStaff);

    /**
     * 导出门店员工
     *
     * @param response        响应
     * @param dealershipStaff 门店员工
     */
    void export(HttpServletResponse response, DealershipStaffMpt dealershipStaff);

    /**
     * 根据门店员工ID获取门店员工信息
     *
     * @param dealershipStaffId 门店员工ID
     * @return 门店员工信息
     */
    AjaxResult getInfo(Long dealershipStaffId);

    /**
     * 新增门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 结果
     */
    AjaxResult add(DealershipStaffMpt dealershipStaff);

    /**
     * 修改保存门店员工
     *
     * @param dealershipStaff 门店信息
     * @return 结果
     */
    AjaxResult edit(DealershipStaffMpt dealershipStaff);

    /**
     * 删除门店员工
     *
     * @param dealershipStaffIds 门店员工ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] dealershipStaffIds);

}
