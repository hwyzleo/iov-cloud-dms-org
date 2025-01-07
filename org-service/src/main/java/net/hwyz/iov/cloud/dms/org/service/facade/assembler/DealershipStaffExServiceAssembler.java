package net.hwyz.iov.cloud.dms.org.service.facade.assembler;

import net.hwyz.iov.cloud.dms.org.api.contract.DealershipStaffExService;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipStaffPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对外服务门店员工转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface DealershipStaffExServiceAssembler {

    DealershipStaffExServiceAssembler INSTANCE = Mappers.getMapper(DealershipStaffExServiceAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param dealershipStaffPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    DealershipStaffExService fromPo(DealershipStaffPo dealershipStaffPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param dealershipStaffExService 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    DealershipStaffPo toPo(DealershipStaffExService dealershipStaffExService);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param dealershipStaffPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<DealershipStaffExService> fromPoList(List<DealershipStaffPo> dealershipStaffPoList);

}
