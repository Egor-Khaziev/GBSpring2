package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public Product dtoToEntity(ProductDto productDto) {
        if (productDto.getCategory()!=null) {
                return new Product(productDto.getId(), productDto.getName(), categoryService.findByName(productDto.getCategory()), productDto.getPrice());
            }
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }


    public ProductDto entityToDto(Product product) {
        if (product.getCategory()!=null) {
            return new ProductDto(product.getId(), product.getName(), product.getCategory().getName(), product.getPrice());
        }
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

}
