package com.algaworks.algafood.domain.service;

import static com.algaworks.algafood.core.constants.MessageConstants.MSG_ENTITY_IN_USE;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.GroupRepository;
import com.algaworks.algafood.domain.vo.GroupVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:07 AM
 */
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public Group save(final Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public Group update(Long groupId, GroupVO groupVO) {
        Group group = findOrFail(groupId);
        mapper.map(groupVO, group);
        return save(group);
    }

    public Group findOrFail(Long groupId) {
        return groupRepository.findOrFail(groupId);
    }

    @Transactional
    public void delete(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
            groupRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(PaymentType.class, groupId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, "Grupo", groupId));
        }  catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Group> list() {
        return groupRepository.findAll();
    }

    @Transactional
    public void bindPermission(final Long groupId, final Long permissionId) {
        Group group = findOrFail(groupId);
        if(isPermissionBonded(group, permissionId)) {
            Permission permission = permissionService.findOrFail(permissionId);
            group.bindPermission(permission);
        }
    }

    @Transactional
    public void unbindPermission(final Long groupId, final Long permissionId) {
        Group group = findOrFail(groupId);
        findPermission(group, permissionId)
                .ifPresent(group::unbindPermission);
    }

    private Optional<Permission> findPermission(Group group, Long permissionId) {
        return group.getPermissions().stream()
                .filter(permission -> permission.getId().equals(permissionId))
                .findFirst();
    }

    private boolean isPermissionBonded(Group group, Long permissionId) {
        return findPermission(group, permissionId).isEmpty();
    }

}
