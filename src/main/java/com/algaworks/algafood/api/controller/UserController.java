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
import com.algaworks.algafood.domain.dto.UserDTO;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;
import com.algaworks.algafood.domain.vo.UserPasswordVO;
import com.algaworks.algafood.domain.vo.UserUpdateVO;
import com.algaworks.algafood.domain.vo.UserVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:01 AM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<UserDTO> list() {
        return userService.list().stream()
                .map(g -> mapper.map(g, UserDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDTO find(@PathVariable Long userId) {
        return mapper.map(userService.findOrFail(userId), UserDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserVO userVO) {
        final User user = mapper.map(userVO, User.class);
        return mapper.map(userService.save(user), UserDTO.class);
    }

    @PutMapping("/{userId}")
    public UserDTO update(@PathVariable Long userId, @RequestBody @Valid UserUpdateVO userVO) {
        return mapper.map(userService.update(userId, userVO), UserDTO.class);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long userId, @RequestBody @Valid UserPasswordVO userPasswordVO) {
        userService.updatePassword(userId, userPasswordVO);
    }

    @GetMapping("/{userId}/groups")
    public List<GroupDTO> list(@PathVariable Long userId) {
        final User user = userService.findOrFail(userId);

        return user.getGroups().stream()
                .map(p -> mapper.map(p, GroupDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindPaymentType(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.bindGroup(userId, groupId);
    }

    @DeleteMapping("/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbindPaymentType(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.unbindGroup(userId, groupId);
    }

}
