package kea.eksamenbackend.participant;

import kea.eksamenbackend.club.Club;
import kea.eksamenbackend.club.ClubRepository;
import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineRepository;
import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ClubRepository clubRepository;
    private final DisciplineRepository disciplineRepository;

    public ParticipantService(ParticipantRepository entity1Repository, ClubRepository clubRepository, DisciplineRepository disciplineRepository) {
        this.participantRepository = entity1Repository;
        this.clubRepository = clubRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public List<ParticipantDTO> findAllParticipants() {
        return participantRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ParticipantDTO> findParticipantById(Long id) {
        if (!participantRepository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return participantRepository.findById(id).map(this::toDTO);
    }

    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        // Fetch the Club entity using the provided club name
        Club club = clubRepository.findByName(participantDTO.getClub().getName())
                .orElseThrow(() -> new NotFoundException("Club not found"));

        // Fetch the Discipline entities using the provided discipline names
        List<Discipline> disciplines = participantDTO.getDiscipline().stream()
                .map(disciplineDTO -> disciplineRepository.findByName(disciplineDTO.getName())
                        .orElseThrow(() -> new NotFoundException("Discipline not found: " + disciplineDTO.getName())))
                .collect(Collectors.toList());

        // Create a new Participant entity and set the fetched Club and Discipline entities
        Participant participant = new Participant(participantDTO.getId(), participantDTO.getName(), participantDTO.getGender(), participantDTO.getAge(), club, disciplines);

        // Save the Participant entity and convert it to a DTO
        return toDTO(participantRepository.save(participant));
    }

    public Optional<ParticipantDTO> updateParticipant(Long id, ParticipantDTO entity1DTO) {
        if (participantRepository.existsById(id)) {
            Participant existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(participantRepository.save(existingEntity1)));
        } else {
            throw new NotFoundException("Entity1 not found");
        }
    }

    public Optional<ParticipantDTO> deleteParticipant(Long id) {
        Optional<Participant> entity1 = participantRepository.findById(id);
        if (entity1.isPresent()) {
            participantRepository.deleteById(id);
            return Optional.of(toDTO(entity1.get()));
        } else {
            throw new NotFoundException("Entity1 not found");
        }
    }

    public ParticipantDTO toDTO (Participant entity1) {
        return new ParticipantDTO(entity1.getId(), entity1.getName(), entity1.getGender(), entity1.getAge(), entity1.getClub(), entity1.getDiscipline());
    }

    public Participant toEntity (ParticipantDTO entity1DTO) {
        return new Participant(entity1DTO.getId(), entity1DTO.getName(), entity1DTO.getGender(), entity1DTO.getAge(), entity1DTO.getClub(), entity1DTO.getDiscipline());
    }
}
