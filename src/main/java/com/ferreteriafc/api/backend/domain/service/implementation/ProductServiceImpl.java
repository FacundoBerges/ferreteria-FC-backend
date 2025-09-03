package com.ferreteriafc.api.backend.domain.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;
import com.ferreteriafc.api.backend.domain.dto.ProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.UpdateProductDTO;
import com.ferreteriafc.api.backend.domain.mapper.ProductMapper;
import com.ferreteriafc.api.backend.domain.service.IProductService;
import com.ferreteriafc.api.backend.persistence.entity.Brand;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.entity.Product;
import com.ferreteriafc.api.backend.persistence.repository.ProductRepository;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.EmptyListException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandServiceImpl brandService;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              BrandServiceImpl brandService,
                              CategoryServiceImpl categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Override
    public ProductDTO save(SaveProductDTO saveProductDTO) {
        String productCode = saveProductDTO.getCode();

        if (productRepository.existsByCode(productCode))
            throw new AlreadyExistException("Product already exists.");

        Product entityProduct = productMapper.toProduct(saveProductDTO);

        Category category = categoryService.findById(saveProductDTO.getCategoryId());
        entityProduct.setCategory(category);

        if (saveProductDTO.getBrandId() != null) {
            Brand brand = brandService.findById(saveProductDTO.getBrandId());
            entityProduct.setBrand(brand);
        }

        return productMapper.toProductDTO( productRepository.save(entityProduct) );
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
            throw new EmptyListException("No products found.");

        return productMapper.toProductDTOList(products);
    }

    @Override
    public ProductDTO findById(Integer id) {
        validateId(id);

        Product product = productRepository
                            .findById(id)
                            .orElseThrow(() -> new NotFoundException("Product not found."));

        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO update(Integer id, UpdateProductDTO updateProductDTO) {
        validateId(id);

        Product repositoryProduct = getProductFromRepository(id)
                                        .orElseThrow(() -> new NotFoundException("Product not found."));

        repositoryProduct.setCode(updateProductDTO.getCode());
        repositoryProduct.setDescription(updateProductDTO.getDescription());

        if (updateProductDTO.getImageUrl() != null &&  !updateProductDTO.getImageUrl().isEmpty()) {
            repositoryProduct.setImgUrl(updateProductDTO.getImageUrl());
        }

        Category category = categoryService.findById(updateProductDTO.getCategoryId());
        repositoryProduct.setCategory(category);

        if (updateProductDTO.getBrandId() != null) {
            Brand brand = brandService.findById(updateProductDTO.getBrandId());
            repositoryProduct.setBrand(brand);
        }

        return productMapper.toProductDTO( productRepository.save(repositoryProduct) );
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        if ( ! productRepository.existsById(id) )
            throw new NotFoundException("Product does not exist.");

        productRepository.deleteById(id);
    }

    private Optional<Product> getProductFromRepository(Integer id) {
        return productRepository.findById(id);
    }

}
