package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.algaworks.algafood.domain.dto.GroupDTO;
import com.algaworks.algafood.domain.dto.PermissionDTO;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;
import com.algaworks.algafood.domain.vo.GroupVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:01 AM
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<GroupDTO> list() {
        return groupService.list().stream()
                .map(g -> mapper.map(g, GroupDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{groupId}")
    public GroupDTO find(@PathVariable Long groupId) {
        return mapper.map(groupService.findOrFail(groupId), GroupDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO save(@RequestBody @Valid GroupVO groupVO) {
        final Group group = mapper.map(groupVO, Group.class);
        return mapper.map(groupService.save(group), GroupDTO.class);
    }

    @PutMapping("/{groupId}")
    public GroupDTO update(@PathVariable Long groupId, @RequestBody @Valid GroupVO groupVO) {
        return mapper.map(groupService.update(groupId, groupVO), GroupDTO.class);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long groupId) {
        groupService.delete(groupId);
    }

    @GetMapping("/{groupId}/permissions")
    public List<PermissionDTO> list(@PathVariable Long groupId) {
        final Group group = groupService.findOrFail(groupId);

        return group.getPermissions().stream()
                .map(p -> mapper.map(p, PermissionDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindPaymentType(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.bindPermission(groupId, permissionId);
    }

    @DeleteMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbindPaymentType(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.unbindPermission(groupId, permissionId);
    }

}
