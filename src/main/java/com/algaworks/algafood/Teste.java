package com.algaworks.algafood;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 2/2/21 8:38 AM
 */
public class Teste {

    public static void main(String[] args) {
        System.out.println(Duration.of(1, ChronoUnit.MINUTES).toMillis());
        System.out.println(1000 * 60);
    }


}
