package com.ecomm.app.controller;

import com.ecomm.app.dto.UserRequest;
import com.ecomm.app.dto.UserResponse;
import com.ecomm.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  @GetMapping
  public ResponseEntity<List<UserResponse>> getUsers() {
    return ResponseEntity.ok(userService.fetchUsers());
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    return userService.getUser(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/add")
  public ResponseEntity<String> addUser(@RequestBody UserRequest user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String> modifyUser(@PathVariable Long id, @RequestBody UserRequest user){
    boolean isUserUpdated = userService.updateExistedUser(id, user);
    if(isUserUpdated)
      return ResponseEntity.ok("User data updated successfully");
    return new ResponseEntity<>("User data not existed", HttpStatus.NOT_FOUND);
  }
}
