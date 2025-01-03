package com.ferreteriafc.api.backend.domain.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.persistence.entity.Brand;

@Mapper(componentModel = "spring")
public interface IBrandMapper {

    @Mappings({
        @Mapping(target = "", source = ""),
        @Mapping(target = "", source = "")
    })
    Brand toBrand(BrandDTO brandDTO);

}
