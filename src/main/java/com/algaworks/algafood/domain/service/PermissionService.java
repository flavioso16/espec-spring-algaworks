package com.algaworks.algafood.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.PermissionRepository;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:07 AM
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper mapper;

    public Permission findOrFail(Long permissionId) {
        return permissionRepository.findOrFail(permissionId);
    }

}
