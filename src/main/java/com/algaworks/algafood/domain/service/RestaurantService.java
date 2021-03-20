package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.algaworks.algafood.api.mapper.RestaurantMapper;
import com.algaworks.algafood.core.validation.ValidationException;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.vo.RestaurantVO;
import com.algaworks.algafood.utils.ModelMergeUtil;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestaurantMapper mapper;

    @Autowired
    private ModelMergeUtil modelMergeUtil;

    @Autowired
    private UserService userService;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        try {

            Kitchen kitchen = kitchenService.findOrFail(restaurant.getKitchen().getId());
            restaurant.setKitchen(kitchen);

            Address address = addressService.findOrFail(restaurant.getAddress().getId());
            restaurant.setAddress(address);

            return restaurantRepository.save(restaurant);

        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public Restaurant partialUpdate(Long restaurantId, Restaurant restaurant) {
        try {
            Restaurant newRestaurant = findOrFail(restaurantId);
            modelMergeUtil.merge(restaurant, newRestaurant);
            validate(newRestaurant, "restaurant");
            return save(newRestaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void validate(final Restaurant restaurant, final String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
        validator.validate(restaurant, bindingResult);
        if(bindingResult.hasErrors())  {
            throw new ValidationException(bindingResult);
        }
    }
    
    @Transactional
    public Restaurant update(Long restaurantId, RestaurantVO restaurantVO) {
        try {
            Restaurant restaurant = findOrFail(restaurantId);
            restaurant.setKitchen(new Kitchen());
            mapper.copy(restaurantVO, restaurant);
            return save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Restaurant findOrFail(Long restauranteId) {
        return restaurantRepository.findOrFail(restauranteId);
    }

    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public void activate(final Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.activate();
    }

    @Transactional
    public void inactivate(final Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.inactivate();
    }

    @Transactional
    public void bindPaymentType(final Long restaurantId, final Long paymentTypeId) {
        Restaurant restaurant = findOrFail(restaurantId);
        if(restaurant.isPaymentTypeBonded(paymentTypeId)) {
            PaymentType paymentType = paymentTypeService.findOrFail(paymentTypeId);
            restaurant.bindPaymentType(paymentType);
        }
    }

    @Transactional
    public void unbindPaymentType(final Long restaurantId, final Long paymentTypeId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.unbindPaymentType(paymentTypeId);
    }

    @Transactional
    public Product includeProduct(final Long restaurantId, final Product productParam) {
        try {

            Restaurant restaurant = findOrFail(restaurantId);
            productParam.setRestaurant(restaurant);
            Product product = productService.save(productParam);
            restaurant.includeProduct(product);
            return product;
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public Product updateProduct(final Long restaurantId, final Long productId, final Product productParam) {
        try {
            Product product = productService.findByIdAndRestaurantId(productId, restaurantId);
            modelMergeUtil.merge(productParam, product);
            return product;

        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void open(Long restaurantId) {
        Restaurant restaurante = findOrFail(restaurantId);
        restaurante.open();
    }

    @Transactional
    public void close(Long restaurantId) {
        Restaurant restaurante = findOrFail(restaurantId);
        restaurante.close();
    }

    @Transactional
    public void bindResponsible(final Long restaurantId, final Long responsibleId) {
        Restaurant restaurant = findOrFail(restaurantId);
        if(restaurant.isResponsibleBounded(responsibleId)) {
            User responsible = userService.findOrFail(responsibleId);
            restaurant.bindResponsible(responsible);
        }
    }

    @Transactional
    public void unbindResponsible(final Long restaurantId, final Long responsibleId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.unbindResponsible(responsibleId);
    }
}
