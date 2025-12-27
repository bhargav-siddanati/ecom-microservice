package com.ecomm.app.service;

import com.ecomm.app.dto.OrderItemDTO;
import com.ecomm.app.dto.OrderResponse;
import com.ecomm.app.entity.*;
import com.ecomm.app.repository.OrderRespository;
import com.ecomm.app.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRespository userRespository;
    private final OrderRespository orderRespository;

    public Optional<OrderResponse> placeOrder(String id) {
        //validate for cartItems
        List<CartItem> cartItems = cartService.getCartItemByUserId(id);
        if(cartItems.isEmpty())
            return Optional.empty();

        //validate for user
        Optional<User> userOpt = userRespository.findById(Long.valueOf(id));
        if(userOpt.isEmpty())
            return Optional.empty();

        User user = userOpt.get();

        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cart -> new OrderItem(
                        null,
                        cart.getProduct(),
                        cart.getQuantity(),
                        cart.getPrice(),
                        order)).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRespository.save(order);

        //clear the cart
        cartService.clearCart(id);

        return Optional.of(mapToOrderResponse(savedOrder));
    }
    private OrderResponse mapToOrderResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(items -> new OrderItemDTO(
                                items.getId(),
                                items.getProduct().getId(),
                                items.getQuantity(),
                                items.getPrice(),
                                items.getPrice().multiply(new BigDecimal(items.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
