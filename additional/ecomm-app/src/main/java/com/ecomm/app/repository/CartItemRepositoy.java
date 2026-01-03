package com.ecomm.app.repository;

import com.ecomm.app.entity.CartItem;
import com.ecomm.app.entity.Product;
import com.ecomm.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepositoy extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    Object deleteByUser(User user);
}
