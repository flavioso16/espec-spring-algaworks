package com.algaworks.algafood.infrastructure.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.DailySalesFilter;
import com.algaworks.algafood.domain.model.dto.DailySales;
import com.algaworks.algafood.domain.service.SalesQueryService;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/30/21 1:08 PM
 */
public class SalesQueryServiceImpl implements SalesQueryService {

    @Override
    public List<DailySales> findDailySales(final DailySalesFilter filter) {
        return null;
    }
}
