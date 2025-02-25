package com.ferreteriafc.api.backend.domain.mapper;

import java.util.List;

import org.mapstruct.*;

import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "categoryName", target = "name"),
            @Mapping(source = "categoryImage", target = "imageUrl"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "products", ignore = true)
    })
    Category toCategory(SaveCategoryDTO saveCategoryDTO);

    @Mappings({
        @Mapping(source = "id", target = "categoryId"),
        @Mapping(source = "name", target = "categoryName"),
        @Mapping(source = "imageUrl", target = "categoryImage")
    })
    CategoryDTO toCategoryDTO(Category category);

    List<CategoryDTO> toCategoryDTOList(List<Category> categoryList);

    @Named("toCategory")
    @Mappings({
        @Mapping(source = "categoryId", target = "id"),
        @Mapping(source = "categoryName", target = "name"),
        @Mapping(source = "categoryImage", target = "imageUrl"),
        @Mapping(target = "products", ignore = true)
    })
    Category toCategory(CategoryDTO categoryDTO);

}
