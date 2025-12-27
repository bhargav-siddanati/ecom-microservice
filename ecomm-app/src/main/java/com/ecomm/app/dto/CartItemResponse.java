package com.ecomm.app.dto;

import com.ecomm.app.entity.Product;
import com.ecomm.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
  private Long id;

  private User user;

  private Product product;

  private Integer quantity;

  private BigDecimal price;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
