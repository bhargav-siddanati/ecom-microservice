package com.ecomm.app.service;

import com.ecomm.app.dto.CartItemRequest;
import com.ecomm.app.dto.CartItemResponse;
import com.ecomm.app.entity.CartItem;
import com.ecomm.app.entity.Product;
import com.ecomm.app.entity.User;
import com.ecomm.app.mapper.TestMapper;
import com.ecomm.app.repository.CartItemRepositoy;
import com.ecomm.app.repository.ProductRepository;
import com.ecomm.app.repository.UserRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepositoy cartItemRepositoy;
    private final UserRespository userRespository;
    private final TestMapper mapper;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt = userRespository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepositoy.findByUserAndProduct(user, product);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepositoy.save(existingCartItem);
        }
        else{
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepositoy.save(item);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRespository.findById(Long.valueOf(userId));
        if(userOpt.isPresent() && productOpt.isPresent()){
            // This required the transactional enabled.
            cartItemRepositoy.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public CartItemResponse findCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepositoy.findById(id);
        if(cartItem.isPresent())
            return mapper.cartItemToResponse(cartItem.get());
        return null;
    }

    public List<CartItem> getCartItemByUserId(String id) {
        return userRespository.findById(Long.valueOf(id))
                .map(cartItemRepositoy::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String id) {
        userRespository.findById(Long.valueOf(id))
                .map(cartItemRepositoy::deleteByUser);
    }
}
