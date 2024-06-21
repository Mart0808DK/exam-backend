package kea.eksamenbackend.discipline;

import kea.eksamenbackend.utils.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DisciplineControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull() {
        assertNotNull(webClient);
    }

    @Test
    void getAllDisciplines() {
        webClient.get().uri("/discipline")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DisciplineDTO.class)
                .hasSize(3);
    }

    @Test
    void createDiscipline() {
        webClient.post().uri("/discipline")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "100 meter",
                            "resultType": "Time"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(4)
                .jsonPath("$.name").isEqualTo("100 meter")
                .jsonPath("$.resultType").isEqualTo("Time");

    }

    @Test
    void updateDiscipline() {
        webClient.put().uri("/discipline/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": 1,
                            "name": "100 meter",
                            "resultType": "Time"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("100 meter")
                .jsonPath("$.resultType").isEqualTo("Time");
    }
}