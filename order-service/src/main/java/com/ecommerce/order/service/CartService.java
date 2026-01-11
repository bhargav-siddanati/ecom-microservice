package com.ecommerce.order.service;

import com.ecommerce.order.client.HttpClientExchangeProvider;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.dto.ProductResponse;
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
  private final HttpClientExchangeProvider provider;

  public boolean addToCart(Long userId, CartItemRequest request) {

    ProductResponse productOpt = provider.getProductById(request.getProductId());

    if (productOpt == null || productOpt.getStockQuantity() < request.getQuantity()) return false;

    /*Optional<User> userOpt = userRespository.findById(Long.valueOf(userId));
    if(userOpt.isEmpty())
        return false;

    User user = userOpt.get();*/

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
