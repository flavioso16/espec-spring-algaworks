package com.algaworks.algafood.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.TestService;

import lombok.Data;
import lombok.ToString;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 11/2/20 1:40 PM
 */
@RestController
@RequestMapping("tests")
public class TestsController {

    private TestService testService;

    public TestsController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String test() {
        this.testService.testUpdateWithTransaction();
        return "ok";
    }


    @GetMapping("/request-param-1")
    public String testRequestParams1(String name) {
        return String.format("Hello %s", name);
    }

    @GetMapping("/request-param-2")
    public String testRequestParams2(@RequestParam(required = false, defaultValue = "default") String value) {
        return String.format("Hello %s", value);
    }

    @GetMapping("/request-param-3")
    public String testRequestParams3(@RequestParam(name="user") String name) {
        return String.format("Hello %s", name);
    }

    @PostMapping
    public String testePost(@RequestBody Cliente... clientes) {
        System.out.println(clientes);
        return "ok";
    }

}

@Data
@ToString
class Cliente {
     private String nome;
}
