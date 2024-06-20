package kea.eksamenbackend.discipline;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {
    private final DisciplineService entity1Service;

    public DisciplineController(DisciplineService entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines() {
        return ResponseEntity.ok(entity1Service.findAllDiscipline());
    }
}
