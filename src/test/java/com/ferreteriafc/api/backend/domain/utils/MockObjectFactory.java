package com.ferreteriafc.api.backend.domain.utils;

import java.util.ArrayList;
import java.util.List;

import com.ferreteriafc.api.backend.persistence.entity.Brand;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;

public class MockObjectFactory {

    public static Category getCategoryEntity() {
        return new Category(1L, "Category 1", "Image URL 1");
    }

    public static CategoryDTO getCategoryDTO() {
        return new CategoryDTO(1L, "Category 1", "Image URL 1");
    }

    public static SaveCategoryDTO getSaveCategoryDTO() {
        return new SaveCategoryDTO("Category 1", "Image URL 1");
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(getCategoryEntity());
        categories.add(new Category(2L, "Category 2", "Image URL 2"));

        return categories;
    }

    public static List<CategoryDTO> getCategoryDTOList() {
        List<CategoryDTO> categories = new ArrayList<>();

        categories.add(getCategoryDTO());
        categories.add(new CategoryDTO(2L, "Category 2", "Image URL 2"));

        return categories;
    }

    public static Brand getBrandEntity() {
        return new Brand(1L, "Brand 1");
    }

    public static BrandDTO getBrandDTO() {
        return new BrandDTO(1L, "Brand 1");
    }

    public static SaveBrandDTO getSaveBrandDTO() {
        return new SaveBrandDTO("Brand 1");
    }

    public static List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();

        brands.add(getBrandEntity());
        brands.add(new Brand(2L, "Brand 2"));

        return brands;
    }

    public static List<BrandDTO> getBrandDTOList() {
        List<BrandDTO> brands = new ArrayList<>();

        brands.add(getBrandDTO());
        brands.add(new BrandDTO(2L, "Brand 2"));

        return brands;
    }

}
