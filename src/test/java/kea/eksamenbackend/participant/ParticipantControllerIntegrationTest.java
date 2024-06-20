package kea.eksamenbackend.participant;

import kea.eksamenbackend.club.Area;
import kea.eksamenbackend.club.Club;
import kea.eksamenbackend.club.ClubDTO;
import kea.eksamenbackend.club.ClubRepository;
import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.discipline.DisciplineRepository;
import kea.eksamenbackend.utils.ResultType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebFluxTest(ParticipantController.class)
@ActiveProfiles("test")
public class ParticipantControllerIntegrationTest {

    @MockBean
    private ParticipantService participantService;

    @MockBean
    private DisciplineRepository disciplineRepository;

    @MockBean
    private ClubRepository clubRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        // Opret discipliner
        DisciplineDTO discipline1 = new DisciplineDTO(1L, "100m Løb", ResultType.Time);
        DisciplineDTO discipline2 = new DisciplineDTO(2L, "Højdespring", ResultType.Points);
        DisciplineDTO discipline3 = new DisciplineDTO(3L, "Diskoskast", ResultType.Distance);

        // Opret klubber
        ClubDTO club1 = new ClubDTO(1L, "Aarhus 1900", 1, Area.Jylland);
        ClubDTO club2 = new ClubDTO(2L, "Aarhus Fremad", 2, Area.Jylland);
        ClubDTO club3 = new ClubDTO(3L, "Aalborg Atletik", 3, Area.Jylland);

        // Opret deltagere
        ParticipantDTO participant1 = new ParticipantDTO(1L, "Test Participant", Gender.Male, 25, club1, Arrays.asList(discipline1, discipline2, discipline3));
        ParticipantDTO participant2 = new ParticipantDTO(2L, "Test Participant2", Gender.Female, 30, club2, Arrays.asList(discipline2, discipline3));

        // Konfigurer mockede repositories
        when(participantService.findAllParticipants()).thenReturn(Arrays.asList(participant1, participant2));
        when(participantService.findParticipantById(1L)).thenReturn(Optional.of(participant1));
        when(participantService.createParticipant(participant1)).thenReturn(participant1);
    }

    @Test
    void getAllParticipants() throws Exception {
        // Udfør GET request
        webTestClient.get().uri("/participants")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBodyList(ParticipantDTO.class)
                .hasSize(2)
                .returnResult();
    }

    @Test
    void getParticipantById() throws Exception {
        webTestClient.get().uri("/participants/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Participant")
                .jsonPath("$.discipline[0].name").isEqualTo("100m Løb")
                .jsonPath("$.age").isEqualTo(25)
                .jsonPath("$.club.name").isEqualTo("Aarhus 1900")
                .jsonPath("$.club.area").isEqualTo("Jylland")
                .jsonPath("$.club.ranking").isEqualTo(1)
                .jsonPath("$.gender").isEqualTo(Gender.Male.toString());
    }

    @Test
    void createParticipant() throws Exception {
        // Opret discipliner
        DisciplineDTO discipline1 = new DisciplineDTO(1L, "100m Løb", ResultType.Time);
        DisciplineDTO discipline2 = new DisciplineDTO(2L, "Højdespring", ResultType.Points);
        DisciplineDTO discipline3 = new DisciplineDTO(3L, "Diskoskast", ResultType.Distance);

        // Opret klub
        ClubDTO club1 = new ClubDTO(1L, "Aarhus 1900", 1, Area.Jylland);

        // Opret en ParticipantDTO med klub og discipliner
        ParticipantDTO participantDTO = new ParticipantDTO(
                1L,
                "Test Participant",
                Gender.Male,
                25,
                club1,
                Arrays.asList(discipline1, discipline2, discipline3)
        );

        // Udfør POST request med ParticipantDTO
        webTestClient.post().uri("/participants")
                .bodyValue(participantDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Participant")
                .jsonPath("$.gender").isEqualTo("Male")
                .jsonPath("$.age").isEqualTo(25)
                .jsonPath("$.club.name").isEqualTo("Aarhus 1900")
                .jsonPath("$.discipline[0].name").isEqualTo("100m Løb")
                .jsonPath("$.discipline[1].name").isEqualTo("Højdespring")
                .jsonPath("$.discipline[2].name").isEqualTo("Diskoskast");
    }

    @Test
    void updateParticipant() {
    }

    @Test
    void deleteParticipant() {
    }
}