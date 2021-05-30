package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.DailySalesFilter;
import com.algaworks.algafood.domain.model.dto.DailySales;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/30/21 1:06 PM
 */
public interface SalesQueryService {
    // TODO Move to repository

    List<DailySales> findDailySales(DailySalesFilter filter);

}
