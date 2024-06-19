package kea.eksamenbackend.entity4;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Entity4Service {
    private final Entity4Repository entity1Repository;

    public Entity4Service(Entity4Repository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<Entity4DTO> findAllEntitys1() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<Entity4DTO> findByIdEntity1(Long id) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return entity1Repository.findById(id).map(this::toDTO);
    }

    public Entity4DTO saveEntity1(Entity4DTO entity1DTO) {
        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public Optional<Entity4DTO> updateIfExistsEntity1(Long id, Entity4DTO entity1DTO) {
        if (entity1Repository.existsById(id)) {
            Entity4 existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(entity1Repository.save(existingEntity1)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Entity4DTO> deleteEntity1(Long id) {
        Optional<Entity4> entity1 = entity1Repository.findById(id);
        if (entity1.isPresent()) {
            entity1Repository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            return Optional.empty();
        }
    }

    public Entity4DTO toDTO (Entity4 entity2) {
        return new Entity4DTO(entity2.getId(), entity2.getName(), entity2.getEmail(), entity2.getAge());
    }

    public Entity4 toEntity (Entity4DTO entity1DTO) {
        return new Entity4(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getEmail(), entity1DTO.getAge());
    }
}
