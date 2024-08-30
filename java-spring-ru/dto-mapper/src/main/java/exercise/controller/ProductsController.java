package exercise.controller;

import exercise.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> showAll(){
        return productRepository.findAll().stream().map(p -> productMapper.map(p)).toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id){
        return productMapper.map(productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§RNF")));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ProductDTO create(@RequestBody ProductCreateDTO dto) {
        Product p = productMapper.map(dto);
        productRepository.save(p);
        return productMapper.map(p);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@PathVariable long id, @RequestBody ProductUpdateDTO dto) {
        Product p = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§RNF"));
        System.out.println("§§id is: " + id);
        System.out.println("§§before price is: " + p.getCost());
        System.out.println("§§new price is: " + dto.getPrice());
        productMapper.update(dto, p);
        System.out.println("§§after price is(1): " + p.getCost());
        productRepository.save(p);
        System.out.println("§§after price is(2): " + p.getCost());
        return productMapper.map(p);
    }
    // END
}
