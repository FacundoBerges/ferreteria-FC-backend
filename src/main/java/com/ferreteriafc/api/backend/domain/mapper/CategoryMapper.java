package com.ferreteriafc.api.backend.domain.mapper;

import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
        @Mapping(source = "id", target = "categoryId"),
        @Mapping(source = "name", target = "categoryName"),
        @Mapping(source = "imageUrl", target = "categoryImage")
    })
    CategoryDTO toCategoryDTO(Category category);

    List<CategoryDTO> toCategoryDTOList(List<Category> categoryList);

    @InheritInverseConfiguration
    Category toCategory(CategoryDTO categoryDTO);
}
