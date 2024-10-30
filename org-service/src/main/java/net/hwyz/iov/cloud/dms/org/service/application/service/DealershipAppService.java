package net.hwyz.iov.cloud.dms.org.service.application.service;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.Dealership;
import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.assembler.DealershipPoAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao.DealershipDao;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售门店应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DealershipAppService {

    private final DealershipDao dealershipDao;

    /**
     * 获取销售门店列表
     *
     * @param serviceType  服务类型
     * @param provinceCode 省级行政区代码
     * @param cityCode     地区级行政区代码
     * @param countyCode   县级行政区代码
     * @param requestLon   请求经度
     * @param requestLat   请求纬度
     * @return 销售门店列表
     */
    public List<Dealership> getDealershipList(DealershipServiceType serviceType, String provinceCode, String cityCode,
                                              String countyCode, String requestLon, String requestLat) {
        Map<String, Object> map = new HashMap<>();
        map.put("serviceType", serviceType.name());
        map.put("provinceCode", provinceCode);
        map.put("cityCode", cityCode);
        map.put("countyCode", countyCode);
        map.put("requestLon", requestLon);
        map.put("requestLat", requestLat);
        List<Dealership> dealershipList = new ArrayList<>();
        dealershipDao.selectPoByMap(map).forEach(dealershipPo -> {
            Dealership dealership = DealershipPoAssembler.INSTANCE.toDto(dealershipPo);
            if (StrUtil.isNotBlank(dealershipPo.getLon()) && StrUtil.isNotBlank(dealershipPo.getLat()) &&
                    StrUtil.isNotBlank(requestLon) && StrUtil.isNotBlank(requestLat)) {
                Ellipsoid reference = Ellipsoid.WGS84;
                GeodeticCalculator geoCalc = new GeodeticCalculator();
                GlobalPosition point1 = new GlobalPosition(Double.parseDouble(dealershipPo.getLat()), Double.parseDouble(dealershipPo.getLon()), 0.0);
                GlobalPosition point2 = new GlobalPosition(Double.parseDouble(requestLat), Double.parseDouble(requestLon), 0.0);
                double distance = geoCalc.calculateGeodeticCurve(reference, point2, point1).getEllipsoidalDistance();
                dealership.setDistance(((double) Math.round(distance / 10) / 100));
            }
            dealershipList.add(dealership);
        });
        return dealershipList.stream().sorted(((o1, o2) -> {
            if (o1.getDistance() != null && o2.getDistance() != null) {
                return (int) (o1.getDistance() - o2.getDistance());
            } else {
                return Integer.MAX_VALUE * -1;
            }
        })).toList();
    }

}
