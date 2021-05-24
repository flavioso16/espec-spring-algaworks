package com.algaworks.algafood.domain.repository.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/23/21 9:09 PM
 */
@Setter
@Getter
public class OrderFilter {

    private Long clientId;

    private Long restaurantId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateInitial;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateFinal;

}
