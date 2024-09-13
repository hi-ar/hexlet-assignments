package exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.BaseEntity;
import jakarta.persistence.EntityManager;

// BEGIN
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReferenceMapper {
    @Autowired EntityManager entityManager;

    public <T extends BaseEntity> T map(Long id, @TargetType Class<T> entityClass) {
        if(id != null){
            return entityManager.find(entityClass, id);
        }
        return null;
    }
}
// END
