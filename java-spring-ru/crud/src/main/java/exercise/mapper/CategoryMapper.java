package exercise.mapper;

import exercise.dto.CategoryCreateDTO;
import exercise.dto.CategoryDTO;
import exercise.model.Category;
import org.mapstruct.*;

// BEGIN
@Mapper(
        uses = {ReferenceMapper.class, JsonNullableMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public abstract class CategoryMapper {
//    @Mapping(target = "name", source = "name") //в ДТО name, в модели name
    public abstract Category map(CategoryCreateDTO dto);

//    @Mapping(target = "name", source = "name")
    public abstract CategoryDTO map(Category cat);

//    @Mapping(target = "name", source = "name") //не сущ категори апдейт дто
//    public abstract void update(Category)
}
// END
