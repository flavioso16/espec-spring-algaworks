package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.mapper.PaymentTypeMapper;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.repository.PaymentTypeRepository;
import com.algaworks.algafood.domain.vo.PaymentTypeVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:07 AM
 */
@Service
public class PaymentTypeService {

    private static final String MSG_PAYMENT_TYPE_IN_USE = "Forma de Pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentTypeMapper mapper;

    @Transactional
    public PaymentType save(final PaymentType paymentType) {
        return paymentTypeRepository.save(paymentType);
    }

    public PaymentType update(Long paymentTypeId, PaymentTypeVO paymentTypeVO) {
        PaymentType paymentType = findOrFail(paymentTypeId);
        mapper.copy(paymentTypeVO, paymentType);
        return save(paymentType);
    }

    public PaymentType findOrFail(Long paymentTypeId) {
        return paymentTypeRepository.findOrFail(paymentTypeId);
    }

    @Transactional
    public void delete(Long paymentTypeId) {
        try {
            paymentTypeRepository.deleteById(paymentTypeId);
            paymentTypeRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(PaymentType.class, paymentTypeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PAYMENT_TYPE_IN_USE, paymentTypeId));
        }  catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<PaymentType> list() {
        return paymentTypeRepository.findAll();
    }

}
