package matan.schemaregistry.config.repository;

import matan.schemaregistry.entity.annotation.ExposeIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

@Configuration
public class RepositoryRestConfigurer {
    @Autowired
    protected void configureIdExposure(RepositoryRestConfiguration repositoryRestConfiguration,
                                       Map<Class<? extends Annotation>, Set<Class<?>>> annotationEntityMap) {
        annotationEntityMap.get(ExposeIds.class).forEach(repositoryRestConfiguration::exposeIdsFor);
    }
}
