package com.ecomm.app.service;

import com.ecomm.app.dto.AddressDTO;
import com.ecomm.app.dto.UserRequest;
import com.ecomm.app.dto.UserResponse;
import com.ecomm.app.entity.Address;
import com.ecomm.app.entity.User;
import com.ecomm.app.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRespository userRespository;

  public List<UserResponse> fetchUsers() {
    return userRespository.findAll().stream()
        .map(this::userMapToUserResponse)
        .collect(Collectors.toList());
  }

  public Optional<UserResponse> getUser(Long id) {
    return userRespository.findById(id).map(this::userMapToUserResponse);
  }

  public String createUser(UserRequest user) {
    User userEntity = new User();
    mapUserRequestToUser(user, userEntity);
    userRespository.save(userEntity);
    return "User added successfully";
  }

  public boolean updateExistedUser(Long id, UserRequest user) {
    return userRespository
        .findById(id)
        .map(
            m -> {
              mapUserRequestToUser(user, m);
              m.setLastName(user.getLastName());
              m.setFirstName(user.getFirstName());
              userRespository.save(m);
              return true;
            })
        .orElse(false);
  }

  private UserResponse userMapToUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(String.valueOf(user.getId()));
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setEmail(user.getEmail());
    response.setPhoneNumber(user.getPhoneNumber());

    AddressDTO address = new AddressDTO();
    if (user.getAddress() != null) {
      address.setStreet(user.getAddress().getStreet());
      address.setCity(user.getAddress().getCity());
      address.setState(user.getAddress().getState());
      address.setCountry(user.getAddress().getCountry());
      address.setZipcode(user.getAddress().getZipcode());
      response.setAddress(address);
    }
    return response;
  }

  private void mapUserRequestToUser(UserRequest user, User entity) {
    if (user.getAddress() != null) {
      Address address = new Address();
      address.setStreet(user.getAddress().getStreet());
      address.setCity(user.getAddress().getCity());
      address.setState(user.getAddress().getState());
      address.setCountry(user.getAddress().getCountry());
      address.setZipcode(user.getAddress().getZipcode());
      entity.setAddress(address);
    }
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setEmail(user.getEmail());
    entity.setPhoneNumber(user.getPhoneNumber());
  }
}
