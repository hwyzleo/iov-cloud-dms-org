package net.hwyz.iov.cloud.dms.org.api.feign.service;

import net.hwyz.iov.cloud.dms.org.api.contract.DealershipExService;
import net.hwyz.iov.cloud.dms.org.api.feign.service.factory.ExDealershipServiceFallbackFactory;
import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 门店相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exDealershipService", value = ServiceNameConstants.DMS_ORG, path = "/service/dealership", fallbackFactory = ExDealershipServiceFallbackFactory.class)
public interface ExDealershipService {

    /**
     * 根据门店代码获取门店信息
     *
     * @param dealershipCode 门店代码
     */
    @GetMapping("/{dealershipCode}")
    DealershipExService getByCode(@PathVariable String dealershipCode);

}
