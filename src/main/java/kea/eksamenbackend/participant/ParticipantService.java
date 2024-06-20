package kea.eksamenbackend.participant;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository entity1Repository) {
        this.participantRepository = entity1Repository;
    }

    public List<ParticipantDTO> findAllEntitys1() {
        return participantRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ParticipantDTO> findByIdEntity1(Long id) {
        if (!participantRepository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return participantRepository.findById(id).map(this::toDTO);
    }

    public ParticipantDTO saveEntity1(ParticipantDTO entity1DTO) {
        return toDTO(participantRepository.save(toEntity(entity1DTO)));
    }

    public Optional<ParticipantDTO> updateIfExistsEntity1(Long id, ParticipantDTO entity1DTO) {
        if (participantRepository.existsById(id)) {
            Participant existingEntity1 = toEntity(entity1DTO);
            existingEntity1.setId(id);
            return Optional.of(toDTO(participantRepository.save(existingEntity1)));
        } else {
            throw new NotFoundException("Entity1 not found");
        }
    }

    public Optional<ParticipantDTO> deleteEntity1(Long id) {
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
