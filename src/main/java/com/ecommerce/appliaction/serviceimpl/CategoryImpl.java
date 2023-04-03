package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.repositotry.CategoryRepository;
import com.ecommerce.appliaction.service.CategoryService;
import com.ecommerce.appliaction.dto.CategoryDTO;
import com.ecommerce.appliaction.entity.Category;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void update(CategoryDTO categoryDTO, Long id) throws NotFoundException {

        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found for id = " + id));

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);

    }

    @Override
    public void create(CategoryDTO categoryDTO) throws AlreadyExists {
        Category category1 = new Category();
        var category=categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
         if(category.getCategoryName() == null ){
            category1.setCategoryName(categoryDTO.getCategoryName());
            category1.setDescription(categoryDTO.getDescription());
            categoryRepository.save(category1);

        }else {
            throw new AlreadyExists("category already exists please change the category ");
        }

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id = " + id));
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Long id) throws NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id = " + id));
    }

    @Override
    public List<Category> getAll() throws EmptyDataException {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EmptyDataException("No categories found", HttpStatus.NOT_FOUND);
        }
        return categories;
    }

}
