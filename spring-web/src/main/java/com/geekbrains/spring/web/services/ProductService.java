package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.converters.ProductXMLConverter;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.ProductRepository;
import com.geekbrains.spring.web.repositories.specifications.ProductsSpecifications;
import com.geekbrains.spring.web.soap.products.ProductXML;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductXMLConverter productXMLConverter;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partName, String category, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partName != null) {
            spec = spec.and(ProductsSpecifications.partName(partName));
        }
        if (category != null) {
            spec = spec.and(ProductsSpecifications.categoryLike(category));
        }

        return productRepository.findAll(spec, PageRequest.of(page - 1, 50));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    //for XML
    public ProductXML getById(Long productId) {
        Product product = findById(productId).get();
        return productXMLConverter.productToXML(product);
    }
    //for XML
    public List<ProductXML> getAllProducts() {
        return productRepository.findAll().stream().map(productXMLConverter::productToXML).collect(Collectors.toList());
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }
}
