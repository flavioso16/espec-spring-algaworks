package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.DailySalesFilter;
import com.algaworks.algafood.domain.model.dto.DailySales;
import com.algaworks.algafood.domain.service.SalesQueryService;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/30/21 1:10 PM
 */
@RestController
@RequestMapping("/insights")
public class InsightsController {

    @Autowired
    private SalesQueryService salesQueryService;

    @GetMapping("/daily-sales")
    public List<DailySales> find(DailySalesFilter filter) {
        return salesQueryService.findDailySales(filter);
    }

}
