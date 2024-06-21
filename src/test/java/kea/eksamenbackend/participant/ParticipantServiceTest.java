package kea.eksamenbackend.participant;

import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.discipline.DisciplineRepository;
import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import kea.eksamenbackend.utils.ResultType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void findParticipantById() {
        // Arrange
        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("Discipline");
        discipline.setResultType(ResultType.Time);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("John Doe");
        participant.setClubName("Club");
        participant.setAge(25);
        participant.setGender(Gender.Male);
        participant.setDiscipline(List.of(discipline));

        // Act
        ParticipantDTO result = participantService.toDTO(participant);

        // Print result
        System.out.println(result);


        // Assert
        assertEquals(participant.getId(), result.getId());
        assertEquals(participant.getName(), result.getName());
        assertEquals(participant.getClubName(), result.getClubName());
        assertEquals(participant.getAge(), result.getAge());
        assertEquals(participant.getGender(), result.getGender());
        assertEquals(participant.getDiscipline().get(0).getId(), result.getDiscipline().get(0).getId());
    }

    @Test
    void createParticipant() {
        // Arrange
        DisciplineDTO disciplineDTO = new DisciplineDTO(1L, "Unknown Discipline", ResultType.Time); // This discipline does not exist

        ParticipantDTO participantDTO = new ParticipantDTO(1L, "John Doe", Gender.Male, 25, "Club", List.of(disciplineDTO));

        when(disciplineRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            participantService.createParticipant(participantDTO);
        });
    }

    @Test
    void updateParticipant() {
        // Arrange
        DisciplineDTO disciplineDTO = new DisciplineDTO(1L, "Unknown Discipline", ResultType.Time);

        ParticipantDTO participantDTO = new ParticipantDTO(1L, "John Doe", Gender.Male, 25, "Club", List.of(disciplineDTO));

        // Act
        when(disciplineRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(participantRepository.findById(2L)).thenReturn(Optional.of(new Participant()));

        // Assert
        assertThrows(NotFoundException.class, () -> {
            participantService.updateParticipant(2L, participantDTO);
        });
    }
}