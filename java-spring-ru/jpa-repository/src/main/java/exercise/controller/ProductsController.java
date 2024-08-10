package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
//    List<Product> byPrice(@RequestParam(required = false, name = "min") Integer min,
//                          @RequestParam(required = false, name = "max") Integer max) {
    List<Product> byPrice(@RequestParam(defaultValue = Integer.MIN_VALUE + "") Integer min,
                          @RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer max) {
        System.out.println("§§§§§ min/max: " + min + "/" + max);

//        if (min == null && max == null) {
//            return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
//        }
//        List<Product> result = productRepository.findByPrice(min, max);
//        List<Product> result = productRepository.findByPriceBetweenOrderByPrice(min, max);
        Sort sort = Sort.by(Sort.Order.asc("price"));
        List<Product> result = productRepository.findByPriceBetween(min, max, sort);
        return result;
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
