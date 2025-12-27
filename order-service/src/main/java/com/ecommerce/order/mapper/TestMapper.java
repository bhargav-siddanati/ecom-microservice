package com.ecommerce.order.mapper;


import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    CartItemResponse cartItemToResponse(CartItem item);
}
