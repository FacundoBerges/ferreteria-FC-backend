package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;
import com.ferreteriafc.api.backend.domain.mapper.ProductMapper;
import com.ferreteriafc.api.backend.persistence.repository.ProductRepository;
import com.ferreteriafc.api.backend.persistence.entity.Product;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;

@Service
public class ProductServiceImpl implements IProductService{

    private final ProductRepository productRepository;
    private final IBrandService brandService;
    private final ICategoryService categoryService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            IBrandService brandService,
            ICategoryService categoryService,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO save(SaveProductDTO saveProductDTO) {
        String productCode = saveProductDTO.getCode();

        if (productRepository.existsByCode(productCode))
            throw new AlreadyExistException("Product already exists.");

        BrandDTO brandDTO = brandService.findById(saveProductDTO.getBrandId());
        CategoryDTO categoryDTO = categoryService.findById(saveProductDTO.getCategoryId());

        ProductDTO productDTO = productMapper.toProductDTO(saveProductDTO);
        productDTO.setBrandDTO(brandDTO);
        productDTO.setCategoryDTO(categoryDTO);

        Product entityProduct = productMapper.toProduct(productDTO);

        return productMapper.toProductDTO( productRepository.save(entityProduct) );
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
            throw new NotFoundException("No products found.");

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
    public ProductDTO update(ProductDTO productDTO) {
        Integer id = productDTO.getId();

        validateId(id);

        if ( ! productRepository.existsById(id) )
            throw new NotFoundException("Product does not exist.");

        Product product = productMapper.toProduct(productDTO);

        return productMapper.toProductDTO( productRepository.save(product) );
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        if ( ! productRepository.existsById(id) )
            throw new NotFoundException("Product does not exist.");

        productRepository.deleteById(id);
    }
}
