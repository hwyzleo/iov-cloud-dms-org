package net.hwyz.iov.cloud.dms.org.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffExService;
import net.hwyz.iov.cloud.dms.org.api.feign.service.ExDealershipStaffService;
import net.hwyz.iov.cloud.framework.common.bean.Page;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 门店员工相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExDealershipStaffServiceFallbackFactory implements FallbackFactory<ExDealershipStaffService> {

    @Override
    public ExDealershipStaffService create(Throwable throwable) {
        return new ExDealershipStaffService() {
            @Override
            public Page<DealershipStaffExService> searchPage(String dealershipCode, Integer pageNum, Integer pageSize) {
                logger.error("门店员工服务分页查询门店员工信息调用失败", throwable);
                return null;
            }
        };
    }
}
