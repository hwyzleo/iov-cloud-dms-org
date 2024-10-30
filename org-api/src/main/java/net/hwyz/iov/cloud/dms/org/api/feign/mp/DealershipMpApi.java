package net.hwyz.iov.cloud.dms.org.api.feign.mp;

import net.hwyz.iov.cloud.dms.org.api.contract.Dealership;
import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.ClientAccount;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.Response;

import java.util.List;

/**
 * 销售门店相关手机接口
 *
 * @author hwyz_leo
 */
public interface DealershipMpApi {

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
    Response<List<Dealership>> getDealershipList(DealershipServiceType serviceType, String provinceCode, String cityCode,
                                                 String countyCode, String requestLon, String requestLat, ClientAccount clientAccount);

}
