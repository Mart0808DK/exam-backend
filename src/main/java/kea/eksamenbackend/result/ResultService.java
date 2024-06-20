package kea.eksamenbackend.result;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    private final ResultRepository entity1Repository;

    public ResultService(ResultRepository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<ResultDTO> findAllEntitys1() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ResultDTO> findByIdEntity1(Long id) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return entity1Repository.findById(id).map(this::toDTO);
    }

    public ResultDTO saveEntity1(ResultDTO entity1DTO) {
        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public Optional<ResultDTO> updateIfExistsEntity1(Long id, ResultDTO entity1DTO) {
        if (entity1Repository.existsById(id)) {
            Result existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(entity1Repository.save(existingEntity1)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ResultDTO> deleteEntity1(Long id) {
        Optional<Result> entity1 = entity1Repository.findById(id);
        if (entity1.isPresent()) {
            entity1Repository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            return Optional.empty();
        }
    }

    public ResultDTO toDTO (Result entity2) {
        return new ResultDTO(entity2.getId(), entity2.getName(), entity2.getEmail(), entity2.getAge());
    }

    public Result toEntity (ResultDTO entity1DTO) {
        return new Result(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getEmail(), entity1DTO.getAge());
    }
}
