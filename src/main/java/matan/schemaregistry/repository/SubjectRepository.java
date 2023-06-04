package matan.schemaregistry.repository;

import matan.schemaregistry.entity.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {
}