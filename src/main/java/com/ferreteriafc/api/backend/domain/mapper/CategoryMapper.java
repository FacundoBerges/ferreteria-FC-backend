package com.ferreteriafc.api.backend.domain.mapper;

import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
        @Mapping(source = "name", target = "categoryName"),
        @Mapping(source = "imageUrl", target = "categoryImage")
    })
    CategoryDTO toCategoryDTO(Category category);

    @InheritInverseConfiguration
    Category toCategory(CategoryDTO categoryDTO);
}
