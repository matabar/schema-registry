package matan.schemaregistry.crud;

import com.fasterxml.jackson.databind.JsonNode;
import matan.schemaregistry.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static matan.schemaregistry.ApiEndpoints.SUBJECTS_ENDPOINT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SubjectCrudTest extends TestBase {
    @Test
    public void createSubject_success() {
        JsonNode requestBody = this.objectMapper.createObjectNode()
                .put("name", "createSubject_success");
        ResponseEntity<JsonNode> responseEntity = this.postForJsonEntity(SUBJECTS_ENDPOINT, requestBody);
        assertCreated(responseEntity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"\t", ""})
    public void createSubject_invalidName(String name) {
        JsonNode requestBody = this.objectMapper.createObjectNode()
                .put("name", name);
        ResponseEntity<String> createResponse = this.postForStringEntity(SUBJECTS_ENDPOINT, requestBody);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
