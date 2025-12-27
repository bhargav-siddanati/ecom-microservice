package com.ecomm.app.controller;

import com.ecomm.app.dto.CartItemRequest;
import com.ecomm.app.dto.CartItemResponse;
import com.ecomm.app.entity.CartItem;
import com.ecomm.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product Out of stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId){
        boolean isDeleted = cartService.deleteItemFromCart(userId, productId);
        return isDeleted? ResponseEntity.noContent().build():
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getCartItemById(@PathVariable Long id){
        CartItemResponse response = cartService.findCartItemById(id);
        return Objects.isNull(response)?ResponseEntity.notFound().build():ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItemByUser(
            @RequestHeader("X-User-ID") String id){
        return ResponseEntity.ok(cartService.getCartItemByUserId(id));
    }
}
