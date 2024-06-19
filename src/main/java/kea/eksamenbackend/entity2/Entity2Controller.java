package kea.eksamenbackend.entity2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entity2")
public class Entity2Controller {
    private final Entity2Service entity1Service;

    public Entity2Controller(Entity2Service entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<Entity2DTO>> getAllEntity2() {
        return ResponseEntity.ok(entity1Service.findAllEntitys1());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Entity2DTO>> getEntity1ById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findByIdEntity1(id));
    }

    @PostMapping
    public ResponseEntity<Entity2DTO> saveEntity2(@RequestBody Entity2DTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.saveEntity1(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Entity2DTO>> updateEntity1(@PathVariable Long id, @RequestBody Entity2DTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateIfExistsEntity1(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Entity2DTO>> deleteEntity2(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteEntity1(id));
    }
}
