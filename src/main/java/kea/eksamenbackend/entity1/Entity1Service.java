package kea.eksamenbackend.entity1;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Entity1Service {
    private final Entity1Repository entity1Repository;

    public Entity1Service(Entity1Repository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<Entity1DTO> findAllEntitys1() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<Entity1DTO> findByIdEntity1(Long id) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return entity1Repository.findById(id).map(this::toDTO);
    }

    public Entity1DTO saveEntity1(Entity1DTO entity1DTO) {
        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public Optional<Entity1DTO> updateIfExistsEntity1(Long id, Entity1DTO entity1DTO) {
        if (entity1Repository.existsById(id)) {
            Entity1 existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(entity1Repository.save(existingEntity1)));
        } else {
            throw new NotFoundException("Entity1 not found");
        }
    }

    public Optional<Entity1DTO> deleteEntity1(Long id) {
        Optional<Entity1> entity1 = entity1Repository.findById(id);
        if (entity1.isPresent()) {
            entity1Repository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            throw new NotFoundException("Entity1 not found");
        }
    }

    public Entity1DTO toDTO (Entity1 entity1) {
        return new Entity1DTO(entity1.getId(), entity1.getName(), entity1.getEmail(), entity1.getAge());
    }

    public Entity1 toEntity (Entity1DTO entity1DTO) {
        return new Entity1(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getEmail(), entity1DTO.getAge());
    }
}
