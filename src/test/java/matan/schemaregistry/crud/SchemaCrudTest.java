package matan.schemaregistry.crud;

import com.fasterxml.jackson.databind.JsonNode;
import matan.schemaregistry.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static matan.schemaregistry.ApiEndpoints.SCHEMAS_ENDPOINT;
import static matan.schemaregistry.ApiEndpoints.SUBJECTS_ENDPOINT;

public class SchemaCrudTest extends TestBase {
    @Test
    public void createSchema_success() throws IOException {
        JsonNode createSubjectRequestBody = this.objectMapper.createObjectNode()
                .put("name", "createSchema_success");
        ResponseEntity<JsonNode> createSubjectResponse = this.postForJsonEntity(SUBJECTS_ENDPOINT, createSubjectRequestBody);
        assertCreated(createSubjectResponse);

        JsonNode createSchemaRequestBody = this.objectMapper.createObjectNode()
                .put("subject", extractSelfUrl(createSubjectResponse))
                .put("schema", this.loadResourceAsString("classpath:schemas/person.schema.json"));
        ResponseEntity<JsonNode> createSchemaResponse = this.postForJsonEntity(SCHEMAS_ENDPOINT, createSchemaRequestBody);
        assertCreated(createSchemaResponse);
    }
}
