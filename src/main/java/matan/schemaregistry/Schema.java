package matan.schemaregistry;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "schema")
public class Schema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;
    @NotBlank
    @Column(nullable = false)
    private String name;

}