package net.hwyz.iov.cloud.dms.org.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipMpt;
import net.hwyz.iov.cloud.dms.org.api.contract.OrgMpt;
import net.hwyz.iov.cloud.dms.org.api.feign.mpt.DealershipMptApi;
import net.hwyz.iov.cloud.dms.org.service.application.service.DealershipAppService;
import net.hwyz.iov.cloud.dms.org.service.application.service.OrgAppService;
import net.hwyz.iov.cloud.dms.org.service.facade.assembler.DealershipMptAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
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
 * 门店相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/dealership")
public class DealershipMptController extends BaseController implements DealershipMptApi {

    private final OrgAppService orgAppService;
    private final DealershipAppService dealershipAppService;

    /**
     * 分页查询门店信息
     *
     * @param dealership 门店信息
     * @return 门店信息列表
     */
    @RequiresPermissions("org:dealership:info:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(DealershipMpt dealership) {
        logger.info("管理后台用户[{}]分页查询门店信息", SecurityUtils.getUsername());
        startPage();
        List<DealershipPo> platformPoList = dealershipAppService.search(dealership.getCode(), dealership.getName(),
                dealership.getRegionCode(), dealership.getAreaCode(), getBeginTime(dealership), getEndTime(dealership));
        List<DealershipMpt> dealershipMptList = DealershipMptAssembler.INSTANCE.fromPoList(platformPoList);
        return getDataTable(platformPoList, dealershipMptList);
    }

    /**
     * 导出门店信息
     *
     * @param response   响应
     * @param dealership 门店信息
     */
    @Log(title = "门店管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("org:dealership:info:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, DealershipMpt dealership) {
        logger.info("管理后台用户[{}]导出门店信息", SecurityUtils.getUsername());
    }

    /**
     * 根据门店ID获取门店信息
     *
     * @param dealershipId 门店ID
     * @return 门店信息
     */
    @RequiresPermissions("org:dealership:info:query")
    @Override
    @GetMapping(value = "/{dealershipId}")
    public AjaxResult getInfo(@PathVariable Long dealershipId) {
        logger.info("管理后台用户[{}]根据门店ID[{}]获取门店信息", SecurityUtils.getUsername(), dealershipId);
        DealershipPo dealershipPo = dealershipAppService.getDealershipById(dealershipId);
        return success(DealershipMptAssembler.INSTANCE.fromPo(dealershipPo));
    }

    /**
     * 新增门店信息
     *
     * @param dealership 门店信息
     * @return 结果
     */
    @Log(title = "门店管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("org:dealership:info:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DealershipMpt dealership) {
        logger.info("管理后台用户[{}]新增门店信息[{}]", SecurityUtils.getUsername(), dealership.getCode());
        if (!dealershipAppService.checkCodeUnique(dealership.getId(), dealership.getCode())) {
            return error("新增门店'" + dealership.getCode() + "'失败，门店代码已存在");
        }
        DealershipPo dealershipPo = DealershipMptAssembler.INSTANCE.toPo(dealership);
        dealershipPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(dealershipAppService.createDealership(dealershipPo));
    }

    /**
     * 修改保存门店信息
     *
     * @param dealership 门店信息
     * @return 结果
     */
    @Log(title = "门店管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("org:dealership:info:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DealershipMpt dealership) {
        logger.info("管理后台用户[{}]修改保存门店信息[{}]", SecurityUtils.getUsername(), dealership.getCode());
        if (!dealershipAppService.checkCodeUnique(dealership.getId(), dealership.getCode())) {
            return error("修改保存门店'" + dealership.getCode() + "'失败，门店代码已存在");
        }
        DealershipPo dealershipPo = DealershipMptAssembler.INSTANCE.toPo(dealership);
        dealershipPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(dealershipAppService.modifyDealership(dealershipPo));
    }

    /**
     * 删除门店信息
     *
     * @param dealershipIds 门店ID数组
     * @return 结果
     */
    @Log(title = "门店管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("org:dealership:info:remove")
    @Override
    @DeleteMapping("/{dealershipIds}")
    public AjaxResult remove(@PathVariable Long[] dealershipIds) {
        logger.info("管理后台用户[{}]删除门店信息[{}]", SecurityUtils.getUsername(), dealershipIds);
        return toAjax(dealershipAppService.deleteDealershipByIds(dealershipIds));
    }

    /**
     * 获取组织树结构
     *
     * @param org 组织架构
     * @return 组织树结构
     */
    @RequiresPermissions("org:dealership:info:list")
    @Override
    @GetMapping(value = "/orgTree")
    public AjaxResult orgTree(OrgMpt org) {
        logger.info("管理后台用户[{}]获取组织树结构", SecurityUtils.getUsername());
        return success(orgAppService.selectOrgTreeList(org.getCode(), org.getName()));
    }
}
