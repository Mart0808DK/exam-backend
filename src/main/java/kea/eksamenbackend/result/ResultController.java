package kea.eksamenbackend.result;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService entity1Service) {
        this.resultService = entity1Service;
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAllResults() {
        return ResponseEntity.ok(resultService.findAllResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> getResultById(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.findResultById(id));
    }

    @PostMapping
    public ResponseEntity<ResultDTO> createResultWithOneParticipant(@RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.status(201).body(resultService.createResultWithOneParticipant(entity1DTO));
    }

    @PostMapping("/participants")
    public ResponseEntity<List<ResultDTO>> createResultsForParticipants(@RequestBody List<ResultDTO> resultDTOs) {
        return ResponseEntity.status(201).body(resultService.createResultsForParticipants(resultDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> updateResult(@PathVariable Long id, @RequestBody ResultDTO entity1DTO) {
        return ResponseEntity.ok(resultService.updateIfResultExist(id, entity1DTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ResultDTO>> deleteResult(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }
}
