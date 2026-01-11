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

  public boolean addToCart(Long userId, CartItemRequest request) {

    ProductResponse productOpt = provider.getProductById(request.getProductId());

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

  public boolean deleteItemFromCart(Long userId, Long productId) {
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

  public List<CartItem> getCartItemByUserId(Long id) {
    return cartItemRepositoy.findByUserId(id);
  }

  public void clearCart(Long id) {
    cartItemRepositoy.deleteByUserId(id);
  }
}
