package matan.schemaregistry.entity;

import lombok.Getter;
import lombok.Setter;
import matan.schemaregistry.entity.annotation.ExposeIds;
import matan.schemaregistry.entity.annotation.ValidJsonSchema;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ExposeIds
@Table(name = "schema")
@Entity(name = "schema")
public class Schema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Field 'subject' is required.")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "subject_id", nullable = false, updatable = false)
    private Subject subject;

    @ValidJsonSchema(message = "Field 'schema' is not a valid jsonschema.")
    @NotBlank(message = "Field 'schema' is required.")
    @Column(name = "schema", nullable = false, updatable = false, columnDefinition = "TEXT")
    private String schema;
}