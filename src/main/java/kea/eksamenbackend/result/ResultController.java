package kea.eksamenbackend.result;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultService entity1Service;

    public ResultController(ResultService entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAllEntity1() {
        return ResponseEntity.ok(entity1Service.findAllResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> getEntity1ById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findResultById(id));
    }

    @PostMapping
    public ResponseEntity<ResultDTO> saveEntity1(@RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.createResultWithOneParticipant(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> updateEntity1(@PathVariable Long id, @RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateIfResultExist(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> deleteEntity1(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteResult(id));
    }
}
