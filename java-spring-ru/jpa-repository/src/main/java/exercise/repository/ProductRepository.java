package exercise.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN

//    @Query("SELECT * FROM products")
//    List<Product> findByPrice();

    //working
//    @Query("SELECT e FROM Product e WHERE price BETWEEN %:min% AND %:max% ORDER BY price ASC")
//    List<Product> findByPrice(@Param("min") Integer min, @Param("max") Integer max);

    List<Product> findByPriceBetweenOrderByPrice(Integer min, Integer max);

    List<Product> findByPriceBetween(Integer min, Integer max, Sort sort);
    // END
}
