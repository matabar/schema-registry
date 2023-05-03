package matan.schemaregistry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchemaControllerTest {
    private final TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value(value = "${local.server.port}")
    protected int port;

    @Test
    public void createSchema_success() throws JsonProcessingException {
        ResponseEntity<String> createResponse = this.restTemplate.postForEntity(
                this.route(),
                this.objectMapper.createObjectNode()
                        .put("name", "cool-schema"),
                String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String schemaUrl = this.objectMapper.readTree(createResponse.getBody()).get("_links").get("schema").get("href").asText();
        ResponseEntity<JsonNode> getResponse = this.restTemplate.getForEntity(schemaUrl, JsonNode.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @ParameterizedTest
    @ValueSource(strings = {"\t", ""})
    public void createSchema_invalidName(String name) {
        ResponseEntity<String> createResponse = this.restTemplate.postForEntity(
                this.route(),
                this.objectMapper.createObjectNode()
                        .put("name", name),
                String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private URI route() {
        return URI.create(String.format("http://localhost:%s/schemas", this.port));
    }
}
