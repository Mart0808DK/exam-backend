package kea.eksamenbackend.participant;

import kea.eksamenbackend.discipline.DisciplineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ParticipantController.class)
@ActiveProfiles("test")
public class ParticipantControllerIntegrationTest {

    @MockBean
    private ParticipantService participantService;

    @MockBean
    private DisciplineRepository disciplineRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {;
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

        // Udfør POST request med ParticipantDTO
    }

    @Test
    void updateParticipant() {
    }

    @Test
    void deleteParticipant() {
    }
}