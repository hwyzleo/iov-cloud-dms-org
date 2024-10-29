package net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao;

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
                .build();
        dealershipDao.insertPo(dealershipPo);
    }

}
