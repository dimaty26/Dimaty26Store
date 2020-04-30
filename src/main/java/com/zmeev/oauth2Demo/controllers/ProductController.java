package com.zmeev.oauth2Demo.controllers;

import com.zmeev.oauth2Demo.entities.Category;
import com.zmeev.oauth2Demo.entities.Product;
import com.zmeev.oauth2Demo.services.CategoryService;
import com.zmeev.oauth2Demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    private static int currentPage = 1;
    private static int pageSize = 5;
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listOfProducts(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 Product product) {

        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Product> productPage = productService.getAllPages(pageable);

        model.addAttribute("productPage", productPage);
        model.addAttribute("product", product);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "products";
    }

    @RequestMapping(value = "/add")
    public String addProduct(@Valid Model model) {
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryList);

        return "add-product";
    }

    @PostMapping("/save")
    public String addProduct(@Valid Product product,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "products";
        }

        productService.save(product);
        model.addAttribute("products", productService.findAll());

        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id,
                                @Valid Product product,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            product.setId(id);
            return "update-product";
        }

        productService.save(product);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        productService.deleteById(id);
        model.addAttribute("products", productService.findAll());
        return "redirect:/products";
    }
}
