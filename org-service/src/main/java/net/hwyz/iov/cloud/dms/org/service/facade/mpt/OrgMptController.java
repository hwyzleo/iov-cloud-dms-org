package net.hwyz.iov.cloud.dms.org.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.OrgMpt;
import net.hwyz.iov.cloud.dms.org.api.feign.mpt.OrgMptApi;
import net.hwyz.iov.cloud.dms.org.service.application.service.OrgAppService;
import net.hwyz.iov.cloud.dms.org.service.facade.assembler.DealershipMptAssembler;
import net.hwyz.iov.cloud.dms.org.service.facade.assembler.OrgMptAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.OrgPo;
import net.hwyz.iov.cloud.framework.audit.annotation.Log;
import net.hwyz.iov.cloud.framework.audit.enums.BusinessType;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.framework.security.annotation.RequiresPermissions;
import net.hwyz.iov.cloud.framework.security.util.SecurityUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织结构相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/org")
public class OrgMptController extends BaseController implements OrgMptApi {

    private final OrgAppService orgAppService;

    /**
     * 查询组织结构
     *
     * @param org 组织结构
     * @return 组织结构列表
     */
    @RequiresPermissions("org:dealership:org:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(OrgMpt org) {
        logger.info("管理后台用户[{}]查询组织结构", SecurityUtils.getUsername());
        List<OrgPo> platformPoList = orgAppService.search(org.getCode(), org.getName(), org.getOrgType(), null,
                getBeginTime(org), getEndTime(org));
        List<OrgMpt> dealershipMptList = OrgMptAssembler.INSTANCE.fromPoList(platformPoList);
        return getDataTable(dealershipMptList);
    }

    /**
     * 查询组织结构（排除节点）
     *
     * @param orgId 组织结构ID
     * @return 组织结构列表
     */
    @RequiresPermissions("org:dealership:org:list")
    @Override
    @GetMapping(value = "/list/exclude/{orgId}")
    public TableDataInfo listExcludeChild(@PathVariable Long orgId) {
        logger.info("管理后台用户[{}]查询组织结构（排除节点[{}]）", SecurityUtils.getUsername(), orgId);
        List<OrgPo> platformPoList = orgAppService.search(null, null, null, null,
                null, null);
        List<OrgMpt> dealershipMptList = OrgMptAssembler.INSTANCE.fromPoList(platformPoList);
        dealershipMptList.removeIf(d -> d.getId().longValue() == orgId || ArrayUtils.contains(StrUtil.splitToArray(d.getAncestors(), ","), orgId + ""));
        return getDataTable(dealershipMptList);
    }

    /**
     * 导出组织结构
     *
     * @param response 响应
     * @param org      组织结构
     */
    @Log(title = "组织结构管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("org:dealership:org:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrgMpt org) {
        logger.info("管理后台用户[{}]导出组织结构", SecurityUtils.getUsername());
    }

    /**
     * 根据组织结构ID获取组织结构
     *
     * @param orgId 组织结构ID
     * @return 组织结构
     */
    @RequiresPermissions("org:dealership:org:query")
    @Override
    @GetMapping(value = "/{orgId}")
    public AjaxResult getInfo(@PathVariable Long orgId) {
        logger.info("管理后台用户[{}]根据组织结构ID[{}]获取组织结构", SecurityUtils.getUsername(), orgId);
        OrgPo orgPo = orgAppService.getOrgById(orgId);
        return success(OrgMptAssembler.INSTANCE.fromPo(orgPo));
    }

    /**
     * 新增组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    @Log(title = "组织结构管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("org:dealership:org:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody OrgMpt org) {
        logger.info("管理后台用户[{}]新增组织结构[{}]", SecurityUtils.getUsername(), org.getCode());
        if (!orgAppService.checkCodeUnique(org.getId(), org.getCode())) {
            return error("新增组织结构'" + org.getCode() + "'失败，组织结构代码已存在");
        }
        OrgPo orgPo = OrgMptAssembler.INSTANCE.toPo(org);
        orgPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(orgAppService.createOrg(orgPo));
    }

    /**
     * 修改保存组织结构
     *
     * @param org 组织结构
     * @return 结果
     */
    @Log(title = "组织结构管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("org:dealership:org:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody OrgMpt org) {
        logger.info("管理后台用户[{}]修改保存组织结构[{}]", SecurityUtils.getUsername(), org.getCode());
        if (!orgAppService.checkCodeUnique(org.getId(), org.getCode())) {
            return error("修改保存组织结构'" + org.getCode() + "'失败，组织结构代码已存在");
        }
        OrgPo orgPo = OrgMptAssembler.INSTANCE.toPo(org);
        orgPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(orgAppService.modifyOrg(orgPo));
    }

    /**
     * 删除组织结构
     *
     * @param orgIds 组织结构ID数组
     * @return 结果
     */
    @Log(title = "组织结构管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("org:dealership:org:remove")
    @Override
    @DeleteMapping("/{orgIds}")
    public AjaxResult remove(@PathVariable Long[] orgIds) {
        logger.info("管理后台用户[{}]删除组织结构[{}]", SecurityUtils.getUsername(), orgIds);
        return toAjax(orgAppService.deleteOrgByIds(orgIds));
    }

}
