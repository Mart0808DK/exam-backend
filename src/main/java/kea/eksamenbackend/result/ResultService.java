package kea.eksamenbackend.result;

import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.discipline.DisciplineService;
import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import kea.eksamenbackend.participant.Participant;
import kea.eksamenbackend.participant.ParticipantDTO;
import kea.eksamenbackend.participant.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // Find the Discipline by name
        Discipline discipline = disciplineService.findByName(resultDTO.getDiscipline().getName());

        // Find the Participant by name
        Participant participant = participantService.findByName(resultDTO.getParticipant().getName());

        // Check if the discipline already exists in the participant's discipline list
        Discipline finalDiscipline = discipline;
        Optional<Discipline> existingDiscipline = participant.getDiscipline().stream()
                .filter(d -> d.getName().equals(finalDiscipline.getName()))
                .findFirst();

        // If the discipline exists, use the existing discipline object
        if (existingDiscipline.isPresent()) {
            discipline = existingDiscipline.get();
        } else {
            // If the discipline does not exist, add it to the participant's discipline list
            participant.getDiscipline().add(discipline);
        }

        // Create a new Result
        Result result = new Result(
                resultDTO.getId(),
                resultDTO.getResultType(),
                resultDTO.getDate(),
                resultDTO.getResultValue(),
                discipline,
                participant
        );

        // Save the Result to the repository
        Result savedResult = resultRepository.save(result);

        // Convert the saved Result to ResultDTO and return it
        return toDTO(savedResult);
    }

    public List<ResultDTO> createResultsForParticipants(List<ResultDTO> resultDTOs) {
        // Find the Discipline by name
        Discipline discipline = disciplineService.findByName(resultDTOs.get(0).getDiscipline().getName());

        // Create a list to store the created Results
        List<Result> results = new ArrayList<>();

        // Loop through each ResultDTO in the input list
        for (ResultDTO resultDTO : resultDTOs) {
            // Find the Participant by name
            Participant participant = participantService.findByName(resultDTO.getParticipant().getName());

            // Add the discipline to the participant's discipline list
            participant.getDiscipline().add(discipline);

            // Create a new Result
            Result result = new Result(
                    resultDTO.getId(),
                    resultDTO.getResultType(),
                    resultDTO.getDate(),
                    resultDTO.getResultValue(),
                    discipline,
                    participant
            );

            // Add the created Result to the list
            results.add(result);
        }

        // Save all the Results to the repository
        List<Result> savedResults = resultRepository.saveAll(results);

        // Convert the saved Results to ResultDTOs and return the list
        return savedResults.stream().map(this::toDTO).collect(Collectors.toList());
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

        // Konverter Participant til ResultParticipantDTO
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

        // Konverter ResultParticipantDTO til Participant
        Participant participant = participantService.findParticipantsById(resultDTO.getParticipant().getId())
                .orElseThrow(() -> new NotFoundException("Participant not found"));

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
