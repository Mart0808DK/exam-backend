package kea.eksamenbackend.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ParticipantControllerIntegrationTest {


    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull() {
        assertNotNull(webClient);
    }

    @Test
    void getAllParticipants() {
        webClient.get().uri("/participants")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ParticipantDTO.class);
    }

    @Test
    void getParticipantById() {
        webClient.get().uri("/participants/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Anders Andersen")
                .jsonPath("$.clubName").isEqualTo("Aalborg Atletikklub")
                .jsonPath("$.age").isEqualTo(25);
    }

    @Test
    void createParticipant() {
        webClient.post().uri("/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                      {
                          "name": "John doe",
                          "gender": "Male",
                          "age": 30,
                          "clubName": "Brøndby Atletikklub",
                          "discipline": [
                              {
                                  "id": 1,
                                  "name": "100m Løb",
                                  "resultType": "Time"
                              },
                              {
                                  "id": 3,
                                  "name": "Diskoskast",
                                  "resultType": "Distance"
                              }
                          ]
                      }
""")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(7)
                .jsonPath("$.name").isEqualTo("John doe")
                .jsonPath("$.clubName").isEqualTo("Brøndby Atletikklub")
                .jsonPath("$.age").isEqualTo(30)
                .jsonPath("$.gender").isEqualTo(Gender.Male.toString())
                .jsonPath("$.discipline[0].id").isEqualTo(1)
                .jsonPath("$.discipline[0].name").isEqualTo("100m Løb");

    }

    @Test
    void updateParticipant() {
        // Opret en deltager
        ParticipantDTO newParticipant = webClient.post().uri("/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                {
                          "name": "John doe",
                          "gender": "Male",
                          "age": 30,
                          "clubName": "Brøndby Atletikklub",
                          "discipline": [
                              {
                                  "id": 1,
                                  "name": "100m Løb",
                                  "resultType": "Time"
                              },
                              {
                                  "id": 3,
                                  "name": "Diskoskast",
                                  "resultType": "Distance"
                              }
                          ]
                      }
            """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(ParticipantDTO.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(newParticipant);

        // Opdater den oprettede deltager
        webClient.put().uri("/participants/" + newParticipant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                {
                          "name": "John doe",
                          "gender": "Male",
                          "age": 35,
                          "clubName": "Brøndby Atletikklub",
                          "discipline": [
                              {
                                  "id": 1,
                                  "name": "100m Løb",
                                  "resultType": "Time"
                              },
                              {
                                  "id": 3,
                                  "name": "Diskoskast",
                                  "resultType": "Distance"
                              }
                          ]
                      }
            """)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(newParticipant.getId())
                .jsonPath("$.name").isEqualTo("John doe")
                .jsonPath("$.gender").isEqualTo("Male")
                .jsonPath("$.age").isEqualTo(35)
                .jsonPath("$.clubName").isEqualTo("Brøndby Atletikklub");

    }

    @Test
    void deleteParticipant() {
        webClient.delete().uri("/participants/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Anders Andersen")
                .jsonPath("$.clubName").isEqualTo("Aalborg Atletikklub")
                .jsonPath("$.age").isEqualTo(25);

    }
}