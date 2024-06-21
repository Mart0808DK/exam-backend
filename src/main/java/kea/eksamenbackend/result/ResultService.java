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
        if (!resultRepository.existsById(id)) throw new NotFoundException("Result not found");
        return resultRepository.findById(id).map(this::toDTO);
    }

    public ResultDTO createResultWithOneParticipant(ResultDTO resultDTO) {

        Discipline discipline = disciplineService.findByName(resultDTO.getDiscipline().getName());

        Participant participant = participantService.findByName(resultDTO.getParticipant().getName());

        Discipline finalDiscipline = discipline;
        Optional<Discipline> existingDiscipline = participant.getDiscipline().stream()
                .filter(d -> d.getName().equals(finalDiscipline.getName()))
                .findFirst();

        if (existingDiscipline.isPresent()) {
            discipline = existingDiscipline.get();
        } else {
            participant.getDiscipline().add(discipline);
        }

        Result result = new Result(
                resultDTO.getId(),
                resultDTO.getResultType(),
                resultDTO.getDate(),
                resultDTO.getResultValue(),
                discipline,
                participant
        );

        Result savedResult = resultRepository.save(result);

        return toDTO(savedResult);
    }

    public List<ResultDTO> createResultsForParticipants(List<ResultDTO> resultDTOs) {
        Discipline discipline = disciplineService.findByName(resultDTOs.get(0).getDiscipline().getName());

        List<Result> results = new ArrayList<>();

        for (ResultDTO resultDTO : resultDTOs) {
            Participant participant = participantService.findByName(resultDTO.getParticipant().getName());

            participant.getDiscipline().add(discipline);

            Result result = new Result(
                    resultDTO.getId(),
                    resultDTO.getResultType(),
                    resultDTO.getDate(),
                    resultDTO.getResultValue(),
                    discipline,
                    participant
            );

            results.add(result);
        }

        List<Result> savedResults = resultRepository.saveAll(results);

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
        DisciplineDTO disciplineDTO = disciplineService.toDTO(result.getDiscipline());

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
        Discipline discipline = disciplineService.toEntity(resultDTO.getDiscipline());

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
