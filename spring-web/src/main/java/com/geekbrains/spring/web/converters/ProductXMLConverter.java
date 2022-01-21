package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.services.CategoryService;

import com.geekbrains.spring.web.soap.products.ProductXML;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductXMLConverter {

    private final CategoryService categoryService;

    public ProductXML productToXML(com.geekbrains.spring.web.entities.Product product){
        ProductXML xml = new ProductXML();
        xml.setId(product.getId());
        xml.setCategory(product.getCategory().getName());
        xml.setPrice(product.getPrice());
        xml.setName(product.getName());
        return xml;
    }

    public com.geekbrains.spring.web.entities.Product XMLToProduct (ProductXML xml){
        com.geekbrains.spring.web.entities.Product product = new com.geekbrains.spring.web.entities.Product();
        product.setName(xml.getName());
        product.setPrice(xml.getPrice());
        product.setCategory(categoryService.findByName(xml.getCategory()));
        return product;
    }

}
