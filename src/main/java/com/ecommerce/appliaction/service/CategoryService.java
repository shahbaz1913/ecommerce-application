package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.CategoryDTO;
import com.ecommerce.appliaction.entity.Category;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {


    void create(CategoryDTO categoryDTO) throws AlreadyExists;



    void update(CategoryDTO categoryDTO, Long id);

    void delete(Long id);

    Category getById(Long id)throws NotFoundException;

    List<Category> getAll()throws EmptyDataException;
}
