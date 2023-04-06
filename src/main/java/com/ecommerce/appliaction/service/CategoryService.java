package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.CategoryDTO;
import com.ecommerce.appliaction.entity.Category;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void create(CategoryDTO categoryDTO) throws AlreadyExists;
    void update(CategoryDTO categoryDTO, Long id);
    void delete(Long id);
    Category getById(Long id)throws NoSuchElementFoundException;
    List<Category> getAll() throws EmptyDataException;
}
