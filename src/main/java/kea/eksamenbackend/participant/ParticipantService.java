package kea.eksamenbackend.participant;

import jakarta.transaction.Transactional;
import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.discipline.DisciplineRepository;
import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import kea.eksamenbackend.result.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public ParticipantService(ParticipantRepository entity1Repository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.participantRepository = entity1Repository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    public List<ParticipantDTO> findAllParticipants() {
        return participantRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ParticipantDTO> findParticipantById(Long id) {
        if (!participantRepository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return participantRepository.findById(id).map(this::toDTO);
    }

    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {

        // Fetch the Discipline entities using the provided discipline names
        List<Discipline> disciplines = participantDTO.getDiscipline().stream()
                .map(disciplineDTO -> disciplineRepository.findByName(disciplineDTO.getName())
                        .orElseThrow(() -> new NotFoundException("Discipline not found: " + disciplineDTO.getName())))
                .collect(Collectors.toList());


        Participant participant = new Participant(participantDTO.getId(), participantDTO.getName(), participantDTO.getGender(), participantDTO.getAge(), participantDTO.getClubName(), disciplines);


        return toDTO(participantRepository.save(participant));
    }


    public Optional<ParticipantDTO> updateParticipant(Long id, ParticipantDTO participantDTO) {

        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participant not found"));


        List<Discipline> disciplines = participantDTO.getDiscipline().stream()
                .map(disciplineDTO -> disciplineRepository.findByName(disciplineDTO.getName())
                        .orElseThrow(() -> new NotFoundException("Discipline not found: " + disciplineDTO.getName())))
                .collect(Collectors.toList());

        existingParticipant.setDiscipline(disciplines);
        existingParticipant.setName(participantDTO.getName());
        existingParticipant.setGender(participantDTO.getGender());
        existingParticipant.setAge(participantDTO.getAge());
        existingParticipant.setClubName(participantDTO.getClubName());

        // Save the updated Participant
        return Optional.of(toDTO(participantRepository.save(existingParticipant)));
    }

    @Transactional
    public Optional<ParticipantDTO> deleteParticipant(Long id) {
        Optional<Participant> participant = participantRepository.findById(id);
        if (participant.isPresent()) {
            // Først slet alle resultater, der er tilknyttet til deltageren
            resultRepository.deleteByParticipantId(id);
            // Derefter slet deltageren
            participantRepository.deleteById(id);
            return Optional.of(toDTO(participant.get()));
        } else {
            throw new NotFoundException("Participant not found");
        }
    }

    public ParticipantDTO toDTO(Participant participant) {

        // Konverter liste af Discipline til liste af DisciplineDTO
        List<DisciplineDTO> disciplineDTOs = participant.getDiscipline().stream()
                .map(discipline -> new DisciplineDTO(
                        discipline.getId(),
                        discipline.getName(),
                        discipline.getResultType()
                ))
                .collect(Collectors.toList());


        return new ParticipantDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClubName(),
                disciplineDTOs
        );
    }

    public Participant toEntity(ParticipantDTO participantDTO) {


        List<Discipline> disciplines = participantDTO.getDiscipline().stream()
                .map(disciplineDTO -> new Discipline(
                        disciplineDTO.getId(),
                        disciplineDTO.getName(),
                        disciplineDTO.getResultType()
                ))
                .collect(Collectors.toList());

        return new Participant(
                participantDTO.getId(),
                participantDTO.getName(),
                participantDTO.getGender(),
                participantDTO.getAge(),
                participantDTO.getClubName(),
                disciplines
        );
    }

    public Participant findByName(String name) {
        return participantRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Participant not found"));
    }

    public Optional<Participant> findParticipantsById(Long id) {
        return participantRepository.findById(id);
    }
}
