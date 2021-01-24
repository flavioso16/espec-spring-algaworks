package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.PaymentTypeMapper;
import com.algaworks.algafood.domain.dto.PaymentTypeDTO;
import com.algaworks.algafood.domain.service.PaymentTypeService;
import com.algaworks.algafood.domain.vo.PaymentTypeVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:01 AM
 */
@RestController
@RequestMapping("/payment_types")
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentTypeMapper mapper;

    @GetMapping
    public List<PaymentTypeDTO> list() {
        return mapper.toListDto(paymentTypeService.list());
    }

    @GetMapping("/{paymentTypeId}")
    public PaymentTypeDTO find(@PathVariable Long paymentTypeId) {
        return mapper.toDto(paymentTypeService.findOrFail(paymentTypeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentTypeDTO save(@RequestBody @Valid PaymentTypeVO paymentTypeVO) {
        return mapper.toDto(paymentTypeService.save(mapper.toEntity(paymentTypeVO)));
    }

    @PutMapping("/{paymentTypeId}")
    public PaymentTypeDTO update(@PathVariable Long paymentTypeId, @RequestBody @Valid PaymentTypeVO paymentTypeVO) {
        return mapper.toDto(paymentTypeService.update(paymentTypeId, paymentTypeVO));
    }

    @DeleteMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long paymentTypeId) {
        paymentTypeService.delete(paymentTypeId);
    }

    

}
