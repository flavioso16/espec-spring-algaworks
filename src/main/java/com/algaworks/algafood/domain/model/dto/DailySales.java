package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/30/21 1:00 PM
 */
@AllArgsConstructor
@Setter
@Getter
public class DailySales {

    private LocalDate date;
    private Long salesAmount;
    private BigDecimal totalBilled;

}
