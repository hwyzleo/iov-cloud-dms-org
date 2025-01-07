package net.hwyz.iov.cloud.dms.org.service.facade.assembler;

import net.hwyz.iov.cloud.dms.org.api.contract.DealershipExService;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.DealershipPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对外服务门店转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface DealershipExServiceAssembler {

    DealershipExServiceAssembler INSTANCE = Mappers.getMapper(DealershipExServiceAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param dealershipPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    DealershipExService fromPo(DealershipPo dealershipPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param dealershipExService 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    DealershipPo toPo(DealershipExService dealershipExService);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param dealershipPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<DealershipExService> fromPoList(List<DealershipPo> dealershipPoList);

}
