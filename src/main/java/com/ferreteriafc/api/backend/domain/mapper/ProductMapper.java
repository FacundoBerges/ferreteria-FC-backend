package com.ferreteriafc.api.backend.domain.mapper;

import java.util.List;

import org.mapstruct.*;

import com.ferreteriafc.api.backend.domain.dto.ProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveProductDTO;
import com.ferreteriafc.api.backend.persistence.entity.Product;

@Mapper(
    componentModel = "spring",
    uses = { CategoryMapper.class, BrandMapper.class }
)
public interface ProductMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "imgUrl", target = "imageUrl"),
        @Mapping(source = "brand", target = "brandDTO"),
        @Mapping(source = "category", target = "categoryDTO")
    })
    ProductDTO toProductDTO(Product product);

    @Mappings({
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "imageUrl", target = "imgUrl"),
        @Mapping(source = "brandDTO", target = "brand"),
        @Mapping(source = "categoryDTO", target = "category", qualifiedByName = "toCategory"),
        @Mapping(target = "id", ignore = true)
    })
    Product toProduct(SaveProductDTO saveProductDTO);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "imageUrl", target = "imgUrl"),
        @Mapping(source = "brandDTO", target = "brand"),
        @Mapping(source = "categoryDTO", target = "category", qualifiedByName = "toCategory"),
    })
    Product toProduct(ProductDTO product);

    List<ProductDTO> toProductDTOList(List<Product> products);

}
