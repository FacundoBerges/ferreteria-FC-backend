package com.ferreteriafc.api.backend.domain.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

import com.ferreteriafc.api.backend.persistence.entity.Brand;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;

@Mapper(componentModel = "spring")
public interface IBrandMapper {

    @Mappings({
        @Mapping(target = "id", source = "brandId"),
        @Mapping(target = "name", source = "brandName")
    })
    Brand toBrand(BrandDTO brandDTO);

    @Mappings({
        @Mapping(target = "name", source = "brandName"),
        @Mapping(target = "id", ignore = true)
    })
    Brand toBrand(SaveBrandDTO brandDTO);

    List<BrandDTO> toBrandDTOList(List<Brand> brands);

    @InheritInverseConfiguration
    BrandDTO toBrandDTO(Brand brand);
}
