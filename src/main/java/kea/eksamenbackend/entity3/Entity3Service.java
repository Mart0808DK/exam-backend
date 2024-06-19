package kea.eksamenbackend.entity3;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Entity3Service {
    private final Entity3Repository entity1Repository;

    public Entity3Service(Entity3Repository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<Entity3DTO> findAllEntitys1() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<Entity3DTO> findByIdEntity1(Long id) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return entity1Repository.findById(id).map(this::toDTO);
    }

    public Entity3DTO saveEntity1(Entity3DTO entity1DTO) {
        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public Optional<Entity3DTO> updateIfExistsEntity1(Long id, Entity3DTO entity1DTO) {
        if (entity1Repository.existsById(id)) {
            Entity3 existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(entity1Repository.save(existingEntity1)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Entity3DTO> deleteEntity1(Long id) {
        Optional<Entity3> entity1 = entity1Repository.findById(id);
        if (entity1.isPresent()) {
            entity1Repository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            return Optional.empty();
        }
    }

    public Entity3DTO toDTO (Entity3 entity2) {
        return new Entity3DTO(entity2.getId(), entity2.getName(), entity2.getEmail(), entity2.getAge());
    }

    public Entity3 toEntity (Entity3DTO entity1DTO) {
        return new Entity3(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getEmail(), entity1DTO.getAge());
    }
}
