package matan.schemaregistry.repository;

import matan.schemaregistry.entity.Schema;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchemaRepository extends PagingAndSortingRepository<Schema, Long>, JpaSpecificationExecutor<Schema> {
}