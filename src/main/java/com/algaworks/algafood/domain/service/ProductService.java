package com.algaworks.algafood.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.repository.ProductRepository;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:07 AM
 */
@Service
public class ProductService {

    private static final String ERROR_MSG_NOT_FOUND = "Resource type Product of ID %d not found.";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public Product save(final Product product) {
        return productRepository.save(product);
    }

    public Product findOrFail(final Long productId) {
        return productRepository.findOrFail(productId);
    }

    public Product findByIdAndRestaurantId(final Long productId, final Long restaurantId) {
        return productRepository.findByIdAndRestaurantId(productId, restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_MSG_NOT_FOUND, productId)));
    }

}
