package net.hwyz.iov.cloud.dms.org.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.DealershipExService;
import net.hwyz.iov.cloud.dms.org.api.feign.service.ExDealershipService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 门店相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExDealershipServiceFallbackFactory implements FallbackFactory<ExDealershipService> {

    @Override
    public ExDealershipService create(Throwable throwable) {
        return new ExDealershipService() {
            @Override
            public DealershipExService getByCode(String dealershipCode) {
                logger.error("门店服务根据门店代码[{}]获取门店信息调用失败", dealershipCode, throwable);
                return null;
            }
        };
    }
}
