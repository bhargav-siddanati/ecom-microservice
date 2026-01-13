package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.mapper.PojoMapper;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final PojoMapper mapper;

    public String creteUser(UserRequest userRequest){
        User user = mapper.userRequestToUser(userRequest);
        repository.save(user);
        return "User added successfully";
    }

    public List<UserResponse> getAllUsers(){
        return repository.findAll()
                .stream()
                .map(mapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(String id){
        log.info("Fetching user with id: {}", id);
        return repository.findById(id)
                .map(mapper::userToUserResponse);
    }

    public boolean updateUser(String id, UserRequest userRequest){
        return repository.findById(id)
                .map(exist -> {
                    User user = mapper.userRequestToUser(userRequest);
                    exist.setFirstName(user.getFirstName());
                    exist.setLastName(user.getLastName());
                    repository.save(exist);
                    return true;
                }).orElse(false);
    }
}
