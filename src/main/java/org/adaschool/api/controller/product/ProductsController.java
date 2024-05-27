package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productsService.save(product);
        URI createdProductUri = URI.create("");
        return ResponseEntity.created(createdProductUri).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsService.all();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        Optional<Product> productOptional = productsService.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        } else {
            throw  new ProductNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Optional<Product> productOptional = productsService.findById(id);
        if (productOptional.isPresent() ) {
            productsService.update(product, id);
            return ResponseEntity.ok(productsService.save(productOptional.get()));
        } else {
            throw  new ProductNotFoundException(id);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        Optional<Product> productOptional = productsService.findById(id);
        if (productOptional.isPresent()) {
            productsService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw  new ProductNotFoundException(id);
        }
    }
}
