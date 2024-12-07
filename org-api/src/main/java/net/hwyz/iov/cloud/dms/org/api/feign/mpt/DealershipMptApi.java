package net.hwyz.iov.cloud.dms.org.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipMpt;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;

/**
 * 门店相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface DealershipMptApi {

    /**
     * 分页查询门店信息
     *
     * @param dealership 门店信息
     * @return 门店信息列表
     */
    TableDataInfo list(DealershipMpt dealership);

    /**
     * 导出门店信息
     *
     * @param response   响应
     * @param dealership 门店信息
     */
    void export(HttpServletResponse response, DealershipMpt dealership);

    /**
     * 根据门店ID获取门店信息
     *
     * @param dealershipId 门店ID
     * @return 门店信息
     */
    AjaxResult getInfo(Long dealershipId);

    /**
     * 新增门店信息
     *
     * @param dealership 门店信息
     * @return 结果
     */
    AjaxResult add(DealershipMpt dealership);

    /**
     * 修改保存门店信息
     *
     * @param dealership 门店信息
     * @return 结果
     */
    AjaxResult edit(DealershipMpt dealership);

    /**
     * 删除门店信息
     *
     * @param dealershipIds 门店ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] dealershipIds);

}
