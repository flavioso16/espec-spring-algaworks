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

import com.algaworks.algafood.api.mapper.CityMapper;
import com.algaworks.algafood.domain.dto.CityDTO;
import com.algaworks.algafood.domain.service.CityService;
import com.algaworks.algafood.domain.vo.CityVO;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper mapper;

    @GetMapping
    public List<CityDTO> list() {
        return mapper.toListDto(cityService.list());
    }

    @GetMapping("/{cityId}")
    public CityDTO find(@PathVariable Long cityId) {
        return mapper.toDto(cityService.findOrFail(cityId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO save(@RequestBody @Valid CityVO city) {
        return mapper.toDto(cityService.save(mapper.toEntity(city)));
    }

    @PutMapping("/{cityId}")
    public CityDTO update(@PathVariable Long cityId, @RequestBody @Valid CityVO city) {
        return mapper.toDto(cityService.update(cityId, city));
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
    }

}
