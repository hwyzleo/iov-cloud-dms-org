package net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import net.hwyz.iov.cloud.tsp.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 销售门店 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-29
 */
@Mapper
public interface DealershipDao extends BaseDao<DealershipPo, Long> {

}
