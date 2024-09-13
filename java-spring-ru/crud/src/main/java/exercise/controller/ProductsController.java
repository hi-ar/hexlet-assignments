package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.JsonNullableMapper;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;
    // добавил
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    // BEGIN
    @GetMapping
    public List<ProductDTO> showAll() {
        return productRepository.findAll().stream().map(p -> productMapper.map(p)).toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("prod not found"));
        return productMapper.map(p);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO dto) {
//        При указании id несуществующей категории должен вернуться ответ с кодом 400 Bad request

        Category c = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Product p = productMapper.map(dto);
        productRepository.save(p);
        return productMapper.map(p);
    }

    @PutMapping(path = "{id}")
    public void update(@PathVariable long id, @RequestBody ProductUpdateDTO dto) {
//        При указании id несуществующей категории также должен вернуться ответ с кодом 400 Bad request
        Product p = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("prod not found"));
// отладка начало
        System.out.println("§§§ categories before: ");
        categoryRepository.findAll().stream().forEach(c -> System.out.println(
                c.getId() + " / " + c.getName() + " / " + c.getProducts().size()));

        System.out.println("§§§ Prod(before) cat id: " + p.getCategory().getId()
                + " title: " + p.getTitle()
                + " price: " + p.getPrice()
                + " cat name: " + p.getCategory().getName());

        System.out.println("§§§ DTO cat id: " + dto.getCategoryId()
                + " title: " + dto.getTitle()
                + " price: " + dto.getPrice());
// отладка конец
        productMapper.update(dto, p);
        if(jsonNullableMapper.isPresent(dto.getCategoryId())) {
            p.setCategory(categoryRepository.findById(dto.getCategoryId().get()).orElseThrow(() -> new ResourceNotFoundException("cat not found")));
        }
//отладка начало
        System.out.println("§§§ Prod(after) cat id: " + p.getCategory().getId()
                + " title: " + p.getTitle()
                + " price: " + p.getPrice()
                + " cat name: " + p.getCategory().getName());

//        ProductDTO pdto = productMapper.map(p);
//
//        System.out.println("§§§ ProdDTO(after) cat id: " + pdto.getCategoryId()
//                + " title: " + pdto.getTitle()
//                + " price: " + pdto.getPrice()
//                + " cat name: " + pdto.getCategoryName());
        System.out.println("±±±before save");

        System.out.println("§§§ categories after: ");
        categoryRepository.findAll().stream().forEach(c -> System.out.println(
                c.getId() + " / " + c.getName() + " / " + c.getProducts().size()));
// отладка конец
        productRepository.save(p);

//отладка начало
        System.out.println("±±±after save");
        System.out.println("§§§ categories after: ");
        categoryRepository.findAll().stream().forEach(c -> System.out.println(
                c.getId() + " / " + c.getName() + " / " + c.getProducts().size()));
//            Category newCat = categoryRepository.findById(dto.getCategoryId().get()).orElseThrow(
//                    () -> new ResourceNotFoundException("cat not found")
//            );



//            System.out.println("§§§ NewCat id: " + newCat.getId() + " name: " + newCat.getName() + " prods: ");
//            newCat.getProducts().stream().map(pr -> System.out.println(pr.getId())).count();
// отладка конец
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void remove(@PathVariable long id) {
        //удалить связи?
        Product p = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("prod not found"));
        productRepository.delete(p);
    }
    // END
}
