package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.repositotry.CategoryRepository;
import com.ecommerce.appliaction.service.CategoryService;
import com.ecommerce.appliaction.dto.CategoryDTO;
import com.ecommerce.appliaction.entity.Category;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/category-api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/addNewCategory")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) throws AlreadyExists {
        categoryService.create(categoryDTO);
        return new ResponseEntity<>("Category Created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateCategoryBy/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody @Valid  CategoryDTO categoryDTO, @PathVariable long id) throws NotFoundException {
         categoryService.update(categoryDTO, id);
        return new ResponseEntity<>("category updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCategoryBy/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws NotFoundException {
        categoryService.delete(id);
        return new ResponseEntity<>("Message : category deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/fetchCategoryBy/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) throws NotFoundException {
        Category Category = this.categoryService.getById(id);
        return new ResponseEntity<>(Category, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/fetchAllCategory")
    public ResponseEntity<List<Category>> getAllCategory() throws EmptyDataException {
        List<Category> all = categoryService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
