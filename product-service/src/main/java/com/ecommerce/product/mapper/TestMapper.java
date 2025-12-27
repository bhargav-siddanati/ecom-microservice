package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    ProductRequest productToProductRequest(Product product);

    ProductResponse productToProductResponse(Product product);

    Product productDTOTOProduct(ProductRequest productRequest);
}
