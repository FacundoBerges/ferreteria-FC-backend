package com.ferreteriafc.api.backend.domain.mapper;

import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = { CategoryMapper.class, BrandMapper.class }
)
public interface ProductMapper {



}
