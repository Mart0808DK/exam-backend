package kea.eksamenbackend.discipline;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import kea.eksamenbackend.utils.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository entity1Repository;

    public DisciplineService(DisciplineRepository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<DisciplineDTO> findAllDiscipline() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public DisciplineDTO createDiscipline(DisciplineDTO entity1DTO) {
        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public DisciplineDTO updateDiscipline(Long id, DisciplineDTO entity1DTO) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Discipline not found");

        if (!id.equals(entity1DTO.getId())) throw new NotFoundException("ID does not match");

        return toDTO(entity1Repository.save(toEntity(entity1DTO)));
    }

    public DisciplineDTO toDTO (Discipline entity2) {
        return new DisciplineDTO(entity2.getId(), entity2.getName(), entity2.getResultType());
    }

    public Discipline toEntity (DisciplineDTO entity1DTO) {
        return new Discipline(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getResultType());
    }

    public Discipline findByName(String name) {
        return entity1Repository.findByName(name).orElseThrow(() -> new NotFoundException("Discipline not found"));
    }
}
