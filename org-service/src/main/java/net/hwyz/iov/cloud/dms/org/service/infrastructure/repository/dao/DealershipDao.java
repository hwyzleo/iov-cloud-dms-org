package net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 销售门店 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-30
 */
@Mapper
public interface DealershipDao extends BaseDao<DealershipPo, Long> {

    /**
     * 通过code查询门店信息
     *
     * @param code 门店编码
     * @return 门店信息
     */
    DealershipPo selectPoByCode(String code);

    /**
     * 批量物理删除门店信息
     *
     * @param ids 门店id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}
