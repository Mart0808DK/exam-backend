package kea.eksamenbackend.entity3;

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
}
