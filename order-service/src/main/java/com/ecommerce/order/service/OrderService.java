package com.ecommerce.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.entity.CartItem;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.repository.OrderRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRespository orderRespository;

    public Optional<OrderResponse> placeOrder(Long id) {
        //validate for cartItems
        List<CartItem> cartItems = cartService.getCartItemByUserId(id);
        if(cartItems.isEmpty())
            return Optional.empty();

        /*//validate for user
        Optional<User> userOpt = userRespository.findById(Long.valueOf(id));
        if(userOpt.isEmpty())
            return Optional.empty();

        User user = userOpt.get();*/

        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order
        Order order = new Order();
        order.setUserId(id);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cart -> new OrderItem(
                        null,
                        cart.getProductId(),
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
                                items.getProductId(),
                                items.getQuantity(),
                                items.getPrice(),
                                items.getPrice().multiply(new BigDecimal(items.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
