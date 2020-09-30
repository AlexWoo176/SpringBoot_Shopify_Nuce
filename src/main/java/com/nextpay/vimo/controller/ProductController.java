package com.nextpay.vimo.controller;

import com.nextpay.vimo.model.Image;
import com.nextpay.vimo.model.Product;
import com.nextpay.vimo.model.Review;
import com.nextpay.vimo.service.IImageService;
import com.nextpay.vimo.service.IProductService;
import com.nextpay.vimo.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProduct() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product1 -> {
            product.setId(product1.getId());
            productService.save(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> {
            productService.remove(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<Iterable<Image>> getAllImageByProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(imageService.findAllByProduct(product), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sale-off")
    public ResponseEntity<Iterable<Product>> getAllProductWithSaleOffGreaterThan() {
        return new ResponseEntity<>(productService.findAllBySaleOffGreaterThanZero(), HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<Iterable<Product>> getAllProductByPriceCondition(@RequestParam("min") int minValue, @RequestParam("max") int maxValue) {
        return new ResponseEntity<>(productService.findAllByPriceCondition(minValue, maxValue), HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<Iterable<Product>> getAllProductLatest() {
        return new ResponseEntity<>(productService.findAllProductOrderByDate(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Product>> getAllProductByName(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(productService.findAllByNameContaining(name), HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Iterable<Review>> getAllReviewByProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(reviewService.findAllByProduct(product), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
