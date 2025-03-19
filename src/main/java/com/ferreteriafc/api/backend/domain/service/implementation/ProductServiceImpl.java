package com.ferreteriafc.api.backend.domain.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;
import com.ferreteriafc.api.backend.domain.mapper.ProductMapper;
import com.ferreteriafc.api.backend.domain.service.IProductService;
import com.ferreteriafc.api.backend.persistence.repository.ProductRepository;
import com.ferreteriafc.api.backend.persistence.entity.Product;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.EmptyListException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;
import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO save(SaveProductDTO saveProductDTO) {
        String productCode = saveProductDTO.getCode();

        if (productRepository.existsByCode(productCode))
            throw new AlreadyExistException("Product already exists.");

        Product entityProduct = productMapper.toProduct(saveProductDTO);

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
    public ProductDTO update(Integer id, ProductDTO productDTO) {
        validateId(id);

        Product repositoryProduct = getProductFromRepository(id)
                                        .orElseThrow(() -> new NotFoundException("Product not found."));
        Product toUpdateProduct = productMapper.toProduct(productDTO);

        repositoryProduct.setCode(toUpdateProduct.getCode());
        repositoryProduct.setDescription(toUpdateProduct.getDescription());
        repositoryProduct.setImgUrl(toUpdateProduct.getImgUrl());
        repositoryProduct.setBrand(toUpdateProduct.getBrand());
        repositoryProduct.setCategory(toUpdateProduct.getCategory());

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
