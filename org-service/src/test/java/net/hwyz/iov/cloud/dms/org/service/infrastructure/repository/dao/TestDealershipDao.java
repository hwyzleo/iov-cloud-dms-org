package net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.dms.org.api.contract.enums.DealershipServiceType;
import net.hwyz.iov.cloud.dms.org.service.BaseTest;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 销售门店 DAO 测试类
 *
 * @author hwyz_leo
 */
public class TestDealershipDao extends BaseTest {

    @Autowired
    private DealershipDao dealershipDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        DealershipPo dealershipPo = DealershipPo.builder()
                .code("NJSA01")
                .name("南京服务中心")
                .address("江苏省南京市江宁路1号")
//                .lon("120.1212")
//                .lat("31.5656")
                .provinceCode("32")
                .cityCode("3201")
                .serviceType(DealershipServiceType.S.name())
                .enable(true)
                .sort(99)
                .build();
        dealershipDao.insertPo(dealershipPo);
    }

}
