package com.ecommerce.order.service;

import com.ecommerce.order.client.ProductHttpClientExchangeProvider;
import com.ecommerce.order.client.UserHttpClientExchangeProvider;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.entity.CartItem;
import com.ecommerce.order.mapper.TestMapper;
import com.ecommerce.order.repository.CartItemRepositoy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
  private final CartItemRepositoy cartItemRepositoy;
  private final TestMapper mapper;
  private final ProductHttpClientExchangeProvider provider;
  private final UserHttpClientExchangeProvider userProvider;
  private int attempt = 0;
//  @CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallback")
  @Retry(name = "retryBreaker", fallbackMethod = "addToCartFallBack")
  public boolean addToCart(String userId, CartItemRequest request) {

    ProductResponse productOpt = provider.getProductById(request.getProductId());
    System.out.println("Attempt count: " + (++attempt));
    if (productOpt == null || productOpt.getStockQuantity() < request.getQuantity()) return false;
    System.out.println("Product fetched: " + productOpt.getName());
    System.out.println("User Started");
    UserResponse userOpt = userProvider.getUserById(String.valueOf(userId));
    System.out.println("after User Started");
    if(userOpt == null)
        return false;
    System.out.println("end User Started");
    CartItem existingCartItem =
        cartItemRepositoy.findByUserIdAndProductId(userId, request.getProductId());
    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
      existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
      cartItemRepositoy.save(existingCartItem);
    } else {
      CartItem item = new CartItem();
      item.setUserId(userId);
      item.setProductId(request.getProductId());
      item.setQuantity(request.getQuantity());
      item.setPrice(BigDecimal.valueOf(1000.00));
      cartItemRepositoy.save(item);
    }
    return true;
  }

  public boolean addToCartFallBack(String userId, CartItemRequest request, Exception exception){
    System.out.println("Fallback method called due to: " + exception.getMessage());
    return false;
  }

  public boolean deleteItemFromCart(String userId, Long productId) {
    CartItem cartItem = cartItemRepositoy.findByUserIdAndProductId(userId, productId);
    if (cartItem != null) {
      // This required the transactional enabled.
      cartItemRepositoy.delete(cartItem);
      return true;
    }
    return false;
  }

  public CartItemResponse findCartItemById(Long id) {
    Optional<CartItem> cartItem = cartItemRepositoy.findById(id);
    if (cartItem.isPresent()) return mapper.cartItemToResponse(cartItem.get());
    return null;
  }

  public List<CartItem> getCartItemByUserId(String id) {
    return cartItemRepositoy.findByUserId(id);
  }

  public void clearCart(String id) {
    cartItemRepositoy.deleteByUserId(id);
  }
}
