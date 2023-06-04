package matan.schemaregistry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestBase {
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected ResourceLoader resourceLoader;
    @Value(value = "${local.server.port}")
    protected int port;

    protected ResponseEntity<JsonNode> postForJsonEntity(String endpoint, JsonNode requestBody) {
        return this.restTemplate.postForEntity(this.createUrl(endpoint), requestBody, JsonNode.class);
    }

    protected ResponseEntity<String> postForStringEntity(String endpoint, JsonNode requestBody) {
        return this.restTemplate.postForEntity(this.createUrl(endpoint), requestBody, String.class);
    }

    protected void assertCreated(ResponseEntity<JsonNode> responseEntity) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String selfUrl = extractSelfUrl(responseEntity);
        ResponseEntity<JsonNode> getResponse = this.restTemplate.getForEntity(selfUrl, JsonNode.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    protected static String extractSelfUrl(ResponseEntity<JsonNode> response) {
        return Optional.of(response)
                .map(HttpEntity::getBody)
                .map(jsonNode -> jsonNode.get("_links"))
                .map(jsonNode -> jsonNode.get("self"))
                .map(jsonNode -> jsonNode.get("href"))
                .map(JsonNode::asText)
                .orElseThrow();
    }

    protected URI createUrl(String endpoint) {
        return URI.create(String.format("http://localhost:%s/%s", this.port, endpoint));
    }

    protected String loadResourceAsString(String location) throws IOException {
        return Files.readString(this.resourceLoader.getResource(location).getFile().toPath());
    }
}
