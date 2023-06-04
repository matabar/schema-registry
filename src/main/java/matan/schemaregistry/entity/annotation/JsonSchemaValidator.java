package matan.schemaregistry.entity.annotation;

import com.networknt.schema.JsonSchemaRef;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.JsonSchema;
import io.vertx.json.schema.JsonSchemaOptions;
import io.vertx.json.schema.SchemaRepository;
import io.vertx.json.schema.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class JsonSchemaValidator implements ConstraintValidator<ValidJsonSchema, String> {
    private static final Validator JSON_SCHEMA_META_SCHEMA_VALIDATOR;

    static {
        try {
            JSON_SCHEMA_META_SCHEMA_VALIDATOR = jsonSchemaMetaValidator();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return JSON_SCHEMA_META_SCHEMA_VALIDATOR.validate(value).getValid();
    }

    private static Validator jsonSchemaMetaValidator() throws IOException, URISyntaxException {
        URL jsonSchemaMetaSchemaResource = Thread.currentThread().getContextClassLoader().getResource("meta.schema.json");
        if (Objects.isNull(jsonSchemaMetaSchemaResource)) {
            throw new FileNotFoundException("meta.schema.json was not found");
        }
        SchemaRepository.create(new JsonSchemaOptions().setBaseUri("http://localhost")).validator()
        String jsonSchemaMetaSchema = Files.readString(Path.of(jsonSchemaMetaSchemaResource.toURI()));
        return JsonSchemaRe Validator.create(JsonSchema.of((JsonObject) Json.decodeValue(jsonSchemaMetaSchema)), );
    }
}
