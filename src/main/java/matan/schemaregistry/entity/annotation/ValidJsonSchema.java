package matan.schemaregistry.entity.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonSchemaValidator.class)
@Documented
public @interface ValidJsonSchema {
    String message() default "json schema is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}