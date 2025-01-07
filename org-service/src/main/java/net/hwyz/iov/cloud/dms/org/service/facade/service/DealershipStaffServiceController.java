package net.hwyz.iov.cloud.dms.org.service.facade.service;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffExService;
import net.hwyz.iov.cloud.dms.org.service.application.service.DealershipStaffAppService;
import net.hwyz.iov.cloud.dms.org.service.facade.assembler.DealershipStaffExServiceAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipStaffPo;
import net.hwyz.iov.cloud.framework.common.bean.Page;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 门店员工相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/dealershipStaff")
public class DealershipStaffServiceController extends BaseController {

    private final DealershipStaffAppService dealershipStaffAppService;

    /**
     * 分页查询门店员工信息
     *
     * @param dealershipCode 门店代码
     */
    @GetMapping("/page")
    public Page<DealershipStaffExService> searchPage(@RequestParam(required = false) String dealershipCode, @RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize) {
        logger.info("分页查询门店员工信息");
        startPage();
        List<DealershipStaffPo> dealershipStaffPoList = dealershipStaffAppService.search(null, null,
                dealershipCode, null, null, null, null, null);
        List<DealershipStaffExService> dealershipStaffExServiceList = DealershipStaffExServiceAssembler.INSTANCE.fromPoList(dealershipStaffPoList);
        return new Page<>(dealershipStaffPoList, dealershipStaffExServiceList);
    }

}
