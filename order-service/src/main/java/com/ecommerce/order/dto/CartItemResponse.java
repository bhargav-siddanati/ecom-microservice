package com.ecommerce.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
  private Long id;

  private Long userId;

  private Long productId;

  private Integer quantity;

  private BigDecimal price;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
