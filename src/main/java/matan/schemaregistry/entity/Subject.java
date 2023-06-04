package matan.schemaregistry.entity;

import lombok.Getter;
import lombok.Setter;
import matan.schemaregistry.entity.annotation.ExposeIds;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ExposeIds
@Table(name = "subject")
@Entity(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Field 'name' is required.")
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "TEXT")
    private String name;
}