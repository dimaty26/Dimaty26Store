package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.Category;
import com.zmeev.oauth2Demo.entities.Product;
import com.zmeev.oauth2Demo.repos.CategoryRepository;
import com.zmeev.oauth2Demo.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepo productRepo, CategoryRepository categoryRepository) {
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> getAllPages(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    public Product save(Product stock) {
        Category category = categoryRepository.findByCategoryName(stock.getCategory().getCategoryName());
        stock.setCategory(category);
        return productRepo.save(stock);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }
}
