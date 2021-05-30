package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

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

    private Date date;
    private Long salesAmount;
    private BigDecimal totalBilled;

}
