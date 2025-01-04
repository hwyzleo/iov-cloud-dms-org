package net.hwyz.iov.cloud.dms.org.service.facade.assembler;

import net.hwyz.iov.cloud.dms.org.api.contract.OrgMpt;
import net.hwyz.iov.cloud.dms.org.service.infrastructure.repository.po.OrgPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台组织结构转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface OrgMptAssembler {

    OrgMptAssembler INSTANCE = Mappers.getMapper(OrgMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param orgPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({
            @Mapping(target = "children", expression = "java(new java.util.ArrayList<>())")
    })
    OrgMpt fromPo(OrgPo orgPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param orgMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    OrgPo toPo(OrgMpt orgMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param orgPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<OrgMpt> fromPoList(List<OrgPo> orgPoList);

}
