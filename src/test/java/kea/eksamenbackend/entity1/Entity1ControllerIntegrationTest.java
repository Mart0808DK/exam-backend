package kea.eksamenbackend.entity1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(Entity1Controller.class)
@ActiveProfiles("test")
public class Entity1ControllerIntegrationTest {

    @MockBean
    private Entity1Service entity1Service;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        Entity1DTO entity1DTO = new Entity1DTO(4L, "name", "mail", 23);
        Entity1DTO entity1DTO1 = new Entity1DTO(5L, "name", "mail", 25);
        Entity1DTO entity1DTO2 = new Entity1DTO(6L, "name", "mail", 27);

        when(entity1Service.findAllEntitys1()).thenReturn(List.of(entity1DTO, entity1DTO1, entity1DTO2));
        when(entity1Service.findByIdEntity1(1L)).thenReturn(Optional.of(entity1DTO));
    }


    @Test
    void getAllEntity1() throws Exception {
        webTestClient.get().uri("/entity1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Entity1DTO.class)
                .hasSize(3)
                .returnResult();

    }

    @Test
    void getEntity1ById() throws Exception{
        webTestClient.get().uri("/entity1/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(4L)
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.email").isEqualTo("mail")
                .jsonPath("$.age").isEqualTo(23);

    }

    @Test
    void saveEntity1() throws Exception {
        Entity1DTO entity1DTO = new Entity1DTO(1L, "name", "mail", 23);
        Entity1DTO savedEntity1DTO = new Entity1DTO(1L, "name", "mail", 23);

        // Define the behavior of the service mock
        when(entity1Service.saveEntity1(any(Entity1DTO.class))).thenReturn(savedEntity1DTO);

        webTestClient.post().uri("/entity1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(entity1DTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.email").isEqualTo("mail")
                .jsonPath("$.age").isEqualTo(23);
    }

    @Test
    void updateEntity1() throws Exception {
        Entity1DTO entity1DTO = new Entity1DTO(1L, "name", "mail", 23);
        Entity1DTO updatedEntity1DTO = new Entity1DTO(1L, "name", "mail", 24);

        when(entity1Service.updateIfExistsEntity1(any(Long.class), any(Entity1DTO.class))).thenReturn(Optional.of(updatedEntity1DTO));

        webTestClient.put().uri("/entity1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(entity1DTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.email").isEqualTo("mail")
                .jsonPath("$.age").isEqualTo(24)
                .returnResult();
    }

    @Test
    void deleteEntity1() {
        Entity1DTO entity1DTO = new Entity1DTO(1L, "name", "mail", 23);
        Entity1DTO deletedEntity1DTO = new Entity1DTO(1L, "name", "mail", 23);

        when(entity1Service.deleteEntity1(any(Long.class))).thenReturn(Optional.of(deletedEntity1DTO));

        webTestClient.delete().uri("/entity1/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.email").isEqualTo("mail")
                .jsonPath("$.age").isEqualTo(23);

    }
}