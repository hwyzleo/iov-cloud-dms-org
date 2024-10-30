package net.hwyz.iov.cloud.dms.org.service.facade.mp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.Dealership;
import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.dms.org.api.feign.mp.DealershipMpApi;
import net.hwyz.iov.cloud.dms.org.service.application.service.DealershipAppService;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.Response;
import net.hwyz.iov.cloud.tsp.framework.commons.util.ParamHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售门店相关手机接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mp/dealership")
public class DealershipMpController implements DealershipMpApi {

    private final DealershipAppService dealershipAppService;

    /**
     * 获取销售门店列表
     *
     * @param serviceType   服务类型
     * @param provinceCode  省级行政区代码
     * @param cityCode      地区级行政区代码
     * @param countyCode    县级行政区代码
     * @param requestLon    请求经度
     * @param requestLat    请求纬度
     * @param clientAccount 终端用户
     * @return 销售门店列表
     */
    @Override
    @GetMapping("")
    public Response<List<Dealership>> getDealershipList(@RequestParam DealershipServiceType serviceType,
                                                        @RequestParam(required = false) String provinceCode,
                                                        @RequestParam(required = false) String cityCode,
                                                        @RequestParam(required = false) String countyCode,
                                                        @RequestParam(required = false) String requestLon,
                                                        @RequestParam(required = false) String requestLat,
                                                        @RequestHeader ClientAccount clientAccount) {
        logger.info("手机客户端[{}]获取服务类型[{}]销售门店列表", ParamHelper.getClientAccountInfo(clientAccount), serviceType);
        return new Response<>(dealershipAppService.getDealershipList(serviceType, provinceCode, cityCode, countyCode, requestLon, requestLat));
    }

}
