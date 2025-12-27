package com.ecommerce.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.TestMapper;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final TestMapper testMapper;

  public ProductResponse createProduct(ProductRequest productRequest) {
    Product product = productRepository.save(testMapper.productDTOTOProduct(productRequest));
    return testMapper.productToProductResponse(product);
  }

  public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
    return productRepository
        .findById(id)
        .map(
            response -> {
              Product request =  testMapper.productDTOTOProduct(productRequest);
              request.setId(response.getId());
              Product product =
                  productRepository.save(request);
              return testMapper.productToProductResponse(product);
            });
  }

    public List<ProductResponse> getAllProducts() {
      return productRepository.findByActiveTrue()
              .stream()
              .map(testMapper::productToProductResponse)
              .collect(Collectors.toList());
    }

    public boolean deleteProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> serachByKeyword(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(testMapper::productToProductResponse)
                .collect(Collectors.toList());
    }
}
