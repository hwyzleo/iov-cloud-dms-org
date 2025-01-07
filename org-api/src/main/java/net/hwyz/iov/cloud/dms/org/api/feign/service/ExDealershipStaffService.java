package net.hwyz.iov.cloud.dms.org.api.feign.service;

import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffExService;
import net.hwyz.iov.cloud.dms.org.api.feign.service.factory.ExDealershipStaffServiceFallbackFactory;
import net.hwyz.iov.cloud.framework.common.bean.Page;
import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 门店员工相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exDealershipStaffService", value = ServiceNameConstants.DMS_ORG, path = "/service/dealershipStaff", fallbackFactory = ExDealershipStaffServiceFallbackFactory.class)
public interface ExDealershipStaffService {

    /**
     * 分页查询门店员工信息
     *
     * @param dealershipCode 门店代码
     */
    @GetMapping("/page")
    Page<DealershipStaffExService> searchPage(@RequestParam(required = false) String dealershipCode,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize);

}
