package net.hwyz.iov.cloud.dms.org.service.application.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.dms.org.api.contract.Dealership;
import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.assembler.DealershipPoAssembler;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao.DealershipDao;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * 查询门店信息
     *
     * @param code      车辆平台代码
     * @param name      车辆平台名称
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<DealershipPo> search(String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return dealershipDao.selectPoByMap(map);
    }

    /**
     * 检查门店代码是否唯一
     *
     * @param dealershipId 门店ID
     * @param code         门店代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long dealershipId, String code) {
        if (ObjUtil.isNull(dealershipId)) {
            dealershipId = -1L;
        }
        DealershipPo dealershipPo = getDealershipByCode(code);
        return !ObjUtil.isNotNull(dealershipPo) || dealershipPo.getId().longValue() == dealershipId.longValue();
    }

    /**
     * 根据主键ID获取门店信息
     *
     * @param id 主键ID
     * @return 门店信息
     */
    public DealershipPo getDealershipById(Long id) {
        return dealershipDao.selectPoById(id);
    }

    /**
     * 根据门店代码获取门店信息
     *
     * @param code 门店代码
     * @return 门店信息
     */
    public DealershipPo getDealershipByCode(String code) {
        return dealershipDao.selectPoByCode(code);
    }

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

    /**
     * 新增门店
     *
     * @param dealership 门店信息
     * @return 结果
     */
    public int createDealership(DealershipPo dealership) {
        return dealershipDao.insertPo(dealership);
    }

    /**
     * 修改门店
     *
     * @param dealership 门店信息
     * @return 结果
     */
    public int modifyDealership(DealershipPo dealership) {
        return dealershipDao.updatePo(dealership);
    }

    /**
     * 批量删除门店
     *
     * @param ids 门店ID数组
     * @return 结果
     */
    public int deleteDealershipByIds(Long[] ids) {
        return dealershipDao.batchPhysicalDeletePo(ids);
    }

}
