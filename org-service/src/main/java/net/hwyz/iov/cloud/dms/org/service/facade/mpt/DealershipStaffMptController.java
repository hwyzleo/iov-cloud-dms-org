package net.hwyz.iov.cloud.dms.org.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffMpt;
import net.hwyz.iov.cloud.dms.org.api.contract.OrgMpt;
import net.hwyz.iov.cloud.dms.org.api.feign.mpt.DealershipStaffMptApi;
import net.hwyz.iov.cloud.dms.org.service.application.service.DealershipAppService;
import net.hwyz.iov.cloud.dms.org.service.application.service.DealershipStaffAppService;
import net.hwyz.iov.cloud.dms.org.service.application.service.OrgAppService;
import net.hwyz.iov.cloud.dms.org.service.facade.assembler.DealershipStaffMptAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipStaffPo;
import net.hwyz.iov.cloud.framework.audit.annotation.Log;
import net.hwyz.iov.cloud.framework.audit.enums.BusinessType;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.framework.security.annotation.RequiresPermissions;
import net.hwyz.iov.cloud.framework.security.util.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门店员工相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/dealershipStaff")
public class DealershipStaffMptController extends BaseController implements DealershipStaffMptApi {

    private final OrgAppService orgAppService;
    private final DealershipAppService dealershipAppService;
    private final DealershipStaffAppService dealershipStaffAppService;

    /**
     * 查询门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 门店员工列表
     */
    @RequiresPermissions("org:dealership:staff:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(DealershipStaffMpt dealershipStaff) {
        logger.info("管理后台用户[{}]查询门店员工", SecurityUtils.getUsername());
        List<DealershipStaffPo> dealershipStaffPoList = dealershipStaffAppService.search(dealershipStaff.getRegionCode(),
                dealershipStaff.getAreaCode(), dealershipStaff.getDealershipCode(), dealershipStaff.getDealershipName(),
                dealershipStaff.getUserName(), dealershipStaff.getPhonenumber(), getBeginTime(dealershipStaff),
                getEndTime(dealershipStaff));
        List<DealershipStaffMpt> dealershipStaffMptList = DealershipStaffMptAssembler.INSTANCE.fromPoList(dealershipStaffPoList);
        return getDataTable(dealershipStaffPoList, dealershipStaffMptList);
    }

    /**
     * 导出门店员工
     *
     * @param response        响应
     * @param dealershipStaff 门店员工
     */
    @Log(title = "门店员工管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("org:dealership:staff:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, DealershipStaffMpt dealershipStaff) {
        logger.info("管理后台用户[{}]导出门店员工", SecurityUtils.getUsername());
    }

    /**
     * 根据门店员工ID获取门店员工
     *
     * @param dealershipStaffId 门店员工ID
     * @return 门店员工
     */
    @RequiresPermissions("org:dealership:staff:query")
    @Override
    @GetMapping(value = "/{dealershipStaffId}")
    public AjaxResult getInfo(@PathVariable Long dealershipStaffId) {
        logger.info("管理后台用户[{}]根据门店员工ID[{}]获取门店员工", SecurityUtils.getUsername(), dealershipStaffId);
        DealershipStaffPo dealershipStaffPo = dealershipStaffAppService.getDealershipStaffById(dealershipStaffId);
        return success(DealershipStaffMptAssembler.INSTANCE.fromPo(dealershipStaffPo));
    }

    /**
     * 新增门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 结果
     */
    @Log(title = "门店员工管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("org:dealership:staff:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DealershipStaffMpt dealershipStaff) {
        logger.info("管理后台用户[{}]新增门店员工[{}]", SecurityUtils.getUsername(), dealershipStaff.getUserName());
        if (!dealershipStaffAppService.checkUserIdUnique(dealershipStaff.getId(), dealershipStaff.getUserId())) {
            return error("新增门店员工'" + dealershipStaff.getUserName() + "'失败，用户ID已存在");
        }
        DealershipStaffPo dealershipStaffPo = DealershipStaffMptAssembler.INSTANCE.toPo(dealershipStaff);
        dealershipStaffPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(dealershipStaffAppService.createDealershipStaff(dealershipStaffPo));
    }

    /**
     * 修改保存门店员工
     *
     * @param dealershipStaff 门店员工
     * @return 结果
     */
    @Log(title = "门店员工管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("org:dealership:staff:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DealershipStaffMpt dealershipStaff) {
        logger.info("管理后台用户[{}]修改保存门店员工[{}]", SecurityUtils.getUsername(), dealershipStaff.getUserName());
        if (!dealershipStaffAppService.checkUserIdUnique(dealershipStaff.getId(), dealershipStaff.getUserId())) {
            return error("修改保存门店员工'" + dealershipStaff.getUserName() + "'失败，用户ID已存在");
        }
        DealershipStaffPo dealershipStaffPo = DealershipStaffMptAssembler.INSTANCE.toPo(dealershipStaff);
        dealershipStaffPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(dealershipStaffAppService.modifyDealershipStaff(dealershipStaffPo));
    }

    /**
     * 删除门店员工
     *
     * @param dealershipStaffIds 门店员工ID数组
     * @return 结果
     */
    @Log(title = "门店员工管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("org:dealership:staff:remove")
    @Override
    @DeleteMapping("/{dealershipStaffIds}")
    public AjaxResult remove(@PathVariable Long[] dealershipStaffIds) {
        logger.info("管理后台用户[{}]删除门店员工[{}]", SecurityUtils.getUsername(), dealershipStaffIds);
        return toAjax(dealershipStaffAppService.deleteDealershipStaffByIds(dealershipStaffIds));
    }

    /**
     * 获取组织树结构
     *
     * @param org 组织架构
     * @return 组织树结构
     */
    @RequiresPermissions("org:dealership:staff:list")
    @Override
    @GetMapping(value = "/orgTree")
    public AjaxResult orgTree(OrgMpt org) {
        logger.info("管理后台用户[{}]获取组织树结构", SecurityUtils.getUsername());
        return success(orgAppService.selectOrgTreeList(org.getCode(), org.getName(), org.getOrgType(), org.getRegionCode()));
    }

    /**
     * 查询员工
     *
     * @param key 关键词
     * @return 员工列表
     */
    @RequiresPermissions("org:dealership:staff:list")
    @Override
    @GetMapping(value = "/searchUser")
    public AjaxResult searchUser(@RequestParam(required = false) String key) {
        logger.info("管理后台用户[{}]查询员工[{}]", SecurityUtils.getUsername(), key);
        return success(dealershipStaffAppService.searchUser(key));
    }

    /**
     * 查询门店
     *
     * @param key 关键词
     * @return 员工列表
     */
    @RequiresPermissions("org:dealership:staff:list")
    @Override
    @GetMapping(value = "/searchDealership")
    public AjaxResult searchDealership(String key) {
        logger.info("管理后台用户[{}]查询门店[{}]", SecurityUtils.getUsername(), key);
        return success(dealershipAppService.searchByKey(key));
    }
}
