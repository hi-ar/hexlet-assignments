package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper{
    @Mapping(source = "categoryId", target = "category") //не cat.id
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.id", target = "categoryId") //cat.id
    public abstract ProductDTO map(Product prod);

    @Mapping(source = "categoryId", target = "category") //не cat.id - так меняется не категория у продукта, а айди внутри кагории
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product prod);
}
// END
