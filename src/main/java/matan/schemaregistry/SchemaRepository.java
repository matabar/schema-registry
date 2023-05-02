package matan.schemaregistry;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SchemaRepository extends PagingAndSortingRepository<Schema, UUID> {
}