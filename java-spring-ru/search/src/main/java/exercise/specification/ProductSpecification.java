package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO searchParams) {
        return titleContains(searchParams.getTitleCont())
                .and(categoryId(searchParams.getCategoryId()))
                .and(priceLt(searchParams.getPriceLt()))
                .and(priceGt(searchParams.getPriceGt()))
                .and(ratingGt(searchParams.getRatingGt()));
    }

    private Specification<Product> titleContains(String searchStr) {
        return ((root, query, criteriaBuilder) -> {
            if (searchStr == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), searchStr); // +"%"
        });
    }

    private Specification<Product> categoryId(Long catId){
        return (root, query, criteriaBuilder) -> catId == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("category").get("id"), catId);
    }

    private Specification<Product> priceLt(Integer price) {
        return ((root, query, criteriaBuilder) -> price == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lt(root.get("price"), price));
    }

    private Specification<Product> priceGt(Integer price) {
        return ((root, query, criteriaBuilder) -> price == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.gt(root.get("price"), price));
    }

    private Specification<Product> ratingGt(Double rating) {
        return ((root, query, criteriaBuilder) -> rating == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.gt(root.get("rating"), rating));
    }
}
// END
