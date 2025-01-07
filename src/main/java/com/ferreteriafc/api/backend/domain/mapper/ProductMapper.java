package com.ferreteriafc.api.backend.domain.mapper;

import com.ferreteriafc.api.backend.persistence.entity.Product;
import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;
import org.mapstruct.*;

import java.util.List;

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
        @Mapping(source = "price", target = "price"),
        @Mapping(source = "brand", target = "brandDTO"),
        @Mapping(source = "category", target = "categoryDTO")
    })
    ProductDTO toProductDTO(Product product);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "imageUrl", target = "imgUrl"),
        @Mapping(source = "price", target = "price"),
        @Mapping(source = "brandDTO", target = "brand"),
        @Mapping(source = "categoryDTO", target = "category")
    })
    Product toProduct(SaveProductDTO productDTO);

    @InheritInverseConfiguration
    Product toProduct(ProductDTO product);

    List<ProductDTO> toProductDTOList(List<Product> products);

}
