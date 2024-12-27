package com.ferreteriafc.api.domain.mapper;

import com.ferreteriafc.api.persistence.entity.Category;
import com.ferreteriafc.api.web.dto.CategoryDTO;

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
