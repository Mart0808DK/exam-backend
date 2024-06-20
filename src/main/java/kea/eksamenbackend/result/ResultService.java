package kea.eksamenbackend.result;

import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.discipline.DisciplineService;
import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import kea.eksamenbackend.participant.Participant;
import kea.eksamenbackend.participant.ParticipantDTO;
import kea.eksamenbackend.participant.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

        private final ResultRepository resultRepository;
        private final DisciplineService disciplineService;
        private final ParticipantService participantService;

        public ResultService(ResultRepository entity1Repository, DisciplineService disciplineService, ParticipantService participantService) {
            this.resultRepository = entity1Repository;
            this.disciplineService = disciplineService;
            this.participantService = participantService;
    }

    public List<ResultDTO> findAllResults() {
        return resultRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ResultDTO> findResultById(Long id) {
        if (!resultRepository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return resultRepository.findById(id).map(this::toDTO);
    }

    public ResultDTO createResultWithOneParticipant(ResultDTO resultDTO) {
        // Find the Participant by name
        Participant participant = participantService.findByName(resultDTO.getParticipant().getName());

        // Find the Discipline by name
        Discipline discipline = disciplineService.findByName(resultDTO.getDiscipline().getName());

        // Create a new Result
        Result result = new Result(
                resultDTO.getId(),
                resultDTO.getResultType(),
                resultDTO.getDate(),
                resultDTO.getResultValue(),
                discipline,
                participant
        );

        // Save the Result
        return toDTO(resultRepository.save(result));
    }

    public Optional<ResultDTO> updateIfResultExist(Long id, ResultDTO entity1DTO) {
        if (resultRepository.existsById(id)) {
            Result existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(resultRepository.save(existingEntity1)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ResultDTO> deleteResult(Long id) {
        Optional<Result> entity1 = resultRepository.findById(id);
        if (entity1.isPresent()) {
            resultRepository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            return Optional.empty();
        }
    }

    public ResultDTO toDTO (Result result) {
        // Konverter Discipline til DisciplineDTO
        DisciplineDTO disciplineDTO = disciplineService.toDTO(result.getDiscipline());

        // Konverter Participant til ParticipantDTO
        ParticipantDTO participantDTO = participantService.toDTO(result.getParticipant());

        return new ResultDTO(
                result.getId(),
                result.getResultType(),
                result.getDate(),
                result.getResultValue(),
                participantDTO,
                disciplineDTO
        );
    }

    public Result toEntity (ResultDTO resultDTO) {
        // Konverter DisciplineDTO til Discipline
        Discipline discipline = disciplineService.toEntity(resultDTO.getDiscipline());

        // Konverter ParticipantDTO til Participant
        Participant participant = participantService.toEntity(resultDTO.getParticipant());

        return new Result(
                resultDTO.getId(),
                resultDTO.getResultType(),
                resultDTO.getDate(),
                resultDTO.getResultValue(),
                discipline,
                participant
        );
    }
}
