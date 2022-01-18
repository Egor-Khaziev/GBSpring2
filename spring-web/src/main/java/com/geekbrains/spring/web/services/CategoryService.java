package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findByName (String name){
        return categoryRepository.findByName(name).get();
    }

}
