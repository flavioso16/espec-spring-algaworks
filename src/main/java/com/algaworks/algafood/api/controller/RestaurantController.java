package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.PaymentTypeMapper;
import com.algaworks.algafood.api.mapper.RestaurantMapper;
import com.algaworks.algafood.domain.dto.PaymentTypeDTO;
import com.algaworks.algafood.domain.dto.ProductDTO;
import com.algaworks.algafood.domain.dto.RestaurantDTO;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.domain.vo.ProductVO;
import com.algaworks.algafood.domain.vo.RestaurantPartialVO;
import com.algaworks.algafood.domain.vo.RestaurantVO;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantMapper mapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<RestaurantDTO> list() {
        return mapper.toListDto(restaurantService.list());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO find(@PathVariable Long restaurantId) {
        return mapper.toDto(restaurantService.findOrFail(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO save(@RequestBody @Valid RestaurantVO restaurant) {
        return mapper.toDto(restaurantService.save(mapper.toEntity(restaurant)));
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDTO update(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantVO restaurant) {

        return mapper.toDto(restaurantService.update(restaurantId, restaurant));
    }

    @PatchMapping("/{restaurantId}")
    public RestaurantDTO partialUpdate(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantPartialVO restaurantVO) {
        final Restaurant restaurant = modelMapper.map(restaurantVO, Restaurant.class);
        return mapper.toDto(restaurantService.partialUpdate(restaurantId, restaurant));
    }

    @PutMapping("/{restaurantId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long restaurantId) {
        restaurantService.activate(restaurantId);
    }

    @PutMapping("/{restaurantId}/inactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long restaurantId) {
        restaurantService.inactivate(restaurantId);
    }

    @GetMapping("/{restaurantId}/payment_types")
    public List<PaymentTypeDTO> listPaymentTypes(@PathVariable Long restaurantId) {
        final Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        return paymentTypeMapper.toListDto(restaurant.getPaymentTypes());
    }

    @PostMapping("/{restaurantId}/payment_types/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindPaymentType(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId) {
        restaurantService.bindPaymentType(restaurantId, paymentTypeId);
    }

    @DeleteMapping("/{restaurantId}/payment_types/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbindPaymentType(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId) {
        restaurantService.unbindPaymentType(restaurantId, paymentTypeId);
    }

    @GetMapping("/{restaurantId}/products")
    public List<ProductDTO> listProducts(@PathVariable Long restaurantId) {
        final Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        return restaurant.getProducts().stream()
                .map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}/products/{productId}")
    public ProductDTO getProduct(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return modelMapper.map(productService.findByIdAndRestaurantId(productId, restaurantId), ProductDTO.class);
    }

    @PostMapping("/{restaurantId}/products")
    public ProductDTO includeProduct(@PathVariable Long restaurantId, @Valid @RequestBody ProductVO productVO) {
        final Product product = modelMapper.map(productVO, Product.class);
        return modelMapper.map(restaurantService.includeProduct(restaurantId, product), ProductDTO.class);
    }

    @PutMapping("/{restaurantId}/products/{productId}")
    public ProductDTO updateProduct(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid @RequestBody ProductVO productVO) {
        final Product product = modelMapper.map(productVO, Product.class);
        return modelMapper.map(restaurantService.updateProduct(restaurantId, productId, product), ProductDTO.class);
    }

    @PutMapping("/{restaurantId}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        restaurantService.open(restaurantId);
    }

    @PutMapping("/{restaurantId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restaurantId) {
        restaurantService.close(restaurantId);
    }

}
