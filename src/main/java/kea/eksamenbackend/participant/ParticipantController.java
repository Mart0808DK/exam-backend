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
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        return ResponseEntity.ok(entity1Service.findAllParticipants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findParticipantById(id));
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.createParticipant(entity1DTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> updateParticipant(@PathVariable Long id, @RequestBody ParticipantDTO entity1DTO) {
        return ResponseEntity.ok(entity1Service.updateParticipant(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDTO>> deleteParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.deleteParticipant(id));
    }
}
