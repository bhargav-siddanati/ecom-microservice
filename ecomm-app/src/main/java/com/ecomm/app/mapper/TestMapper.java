package com.ecomm.app.mapper;

import com.ecomm.app.dto.CartItemResponse;
import com.ecomm.app.dto.ProductRequest;
import com.ecomm.app.dto.ProductResponse;
import com.ecomm.app.entity.CartItem;
import com.ecomm.app.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    ProductRequest productToProductRequest(Product product);

    ProductResponse productToProductResponse(Product product);

    Product productDTOTOProduct(ProductRequest productRequest);

    CartItemResponse cartItemToResponse(CartItem item);
}
