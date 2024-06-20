package kea.eksamenbackend.result;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entity4")
public class ResultController {
    private final ResultService entity1Service;

    public ResultController(ResultService entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAllEntity1() {
        return ResponseEntity.ok(entity1Service.findAllEntitys1());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> getEntity1ById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findByIdEntity1(id));
    }

    @PostMapping
    public ResponseEntity<ResultDTO> saveEntity1(@RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.saveEntity1(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> updateEntity1(@PathVariable Long id, @RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateIfExistsEntity1(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> deleteEntity1(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteEntity1(id));
    }
}
