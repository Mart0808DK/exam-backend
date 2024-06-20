package kea.eksamenbackend.discipline;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
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
