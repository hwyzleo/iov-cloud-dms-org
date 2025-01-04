package net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipStaffPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 销售门店员工 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-12-30
 */
@Mapper
public interface DealershipStaffDao extends BaseDao<DealershipStaffPo, Long> {

}
