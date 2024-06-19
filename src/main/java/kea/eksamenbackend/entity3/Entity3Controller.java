package kea.eksamenbackend.entity3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entity3")
public class Entity3Controller {
    private final Entity3Service entity1Service;

    public Entity3Controller(Entity3Service entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<Entity3DTO>> getAllEntity1() {
        return ResponseEntity.ok(entity1Service.findAllEntitys1());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Entity3DTO>> getEntity1ById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findByIdEntity1(id));
    }

    @PostMapping
    public ResponseEntity<Entity3DTO> saveEntity1(@RequestBody Entity3DTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.saveEntity1(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Entity3DTO>> updateEntity1(@PathVariable Long id, @RequestBody Entity3DTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateIfExistsEntity1(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Entity3DTO>> deleteEntity1(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteEntity1(id));
    }
}
