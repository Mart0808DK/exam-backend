package kea.eksamenbackend.club;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/club")
public class ClubController {
    private final ClubService entity1Service;

    public ClubController(ClubService entity1Service) {
        this.entity1Service = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        return ResponseEntity.ok(entity1Service.findAllClubs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClubDTO>> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(entity1Service.findClubById(id));
    }

    @PostMapping
    public ResponseEntity<ClubDTO> createClub(@RequestBody ClubDTO entity1DTO) {
        return ResponseEntity.status(201).body(entity1Service.createClub(entity1DTO));
    }
}
