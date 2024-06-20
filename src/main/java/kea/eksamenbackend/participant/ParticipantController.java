package kea.eksamenbackend.participant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private final ParticipantService entity1Service;

    public ParticipantController(ParticipantService entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllEntity1() {
        return ResponseEntity.ok(entity1Service.findAllEntitys1());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> getEntity1ById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findByIdEntity1(id));
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> saveEntity1(@RequestBody ParticipantDTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.saveEntity1(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> updateEntity1(@PathVariable Long id, @RequestBody ParticipantDTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateIfExistsEntity1(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> deleteEntity1(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteEntity1(id));
    }
}
