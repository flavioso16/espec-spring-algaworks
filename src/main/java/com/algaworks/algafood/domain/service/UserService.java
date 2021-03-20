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

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;
import com.algaworks.algafood.domain.vo.UserPasswordVO;
import com.algaworks.algafood.domain.vo.UserUpdateVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:07 AM
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public User save(final User user) {
        final Boolean emailAlreadyInUse = userRepository.existsByEmailAndIdNot(user.getEmail(), user.getId());
        if(emailAlreadyInUse) {
            throw new BusinessException(
                    String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long userId, UserUpdateVO userVO) {
        User user = findOrFail(userId);
        mapper.map(userVO, user);
        return save(user);
    }

    @Transactional
    public void updatePassword(Long userId, UserPasswordVO userPasswordVO) {
        // TODO encodar password
        User user = findOrFail(userId);
        if(user.doesPasswordNotMatch(userPasswordVO.getPassword())) {
            throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
        }
        user.setPassword(userPasswordVO.getNewPassword());
    }

    public User findOrFail(Long userId) {
        return userRepository.findOrFail(userId);
    }

    @Transactional
    public void delete(Long userId) {
        try {
            userRepository.deleteById(userId);
            userRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(PaymentType.class, userId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, "User", userId));
        }  catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    @Transactional
    public void bindGroup(final Long userId, final Long groupId) {
        User user = findOrFail(userId);
        if(isGroupBounded(user, groupId)) {
            Group group = groupService.findOrFail(groupId);
            user.bindGroup(group);
        }
    }

    @Transactional
    public void unbindGroup(final Long userId, final Long groupId) {
        User user = findOrFail(userId);
        findGroup(user, groupId)
                .ifPresent(user::unbindGroup);
    }

    private Optional<Group> findGroup(User user, Long groupId) {
        return user.getGroups().stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst();
    }

    private boolean isGroupBounded(User user, Long groupId) {
        return findGroup(user, groupId).isEmpty();
    }

}
