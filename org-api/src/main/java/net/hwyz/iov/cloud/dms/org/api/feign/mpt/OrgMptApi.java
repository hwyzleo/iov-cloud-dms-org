package net.hwyz.iov.cloud.dms.org.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.dms.org.api.contract.OrgMpt;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;

/**
 * 组织结构相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface OrgMptApi {

    /**
     * 查询组织结构
     *
     * @param org 组织结构
     * @return 组织结构列表
     */
    TableDataInfo list(OrgMpt org);

    /**
     * 查询组织结构（排序节点）
     *
     * @param orgId 组织结构ID
     * @return 组织结构列表
     */
    TableDataInfo listExcludeChild(Long orgId);

    /**
     * 导出组织结构
     *
     * @param response 响应
     * @param org      组织结构
     */
    void export(HttpServletResponse response, OrgMpt org);

    /**
     * 根据组织结构ID获取组织结构信息
     *
     * @param orgId 组织结构ID
     * @return 组织结构
     */
    AjaxResult getInfo(Long orgId);

    /**
     * 新增组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    AjaxResult add(OrgMpt org);

    /**
     * 修改保存组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    AjaxResult edit(OrgMpt org);

    /**
     * 删除组织结构
     *
     * @param orgIds 组织结构ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] orgIds);

}
