package matan.schemaregistry.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AnnotationEntityMapper {
    @Bean
    private static Map<Class<? extends Annotation>, Set<Class<?>>> annotationEntityMap(Set<Class<?>> entityDefinitions) {
        Map<Class<? extends Annotation>, Set<Class<?>>> annotationMap = new HashMap<>();
        for (Class<?> entity : entityDefinitions) {
            Annotation[] annotations = entity.getAnnotations();
            for (Annotation annotation : annotations) {
                annotationMap.computeIfAbsent(annotation.annotationType(), k -> new HashSet<>()).add(entity);
            }
        }
        return annotationMap;
    }

    @Bean
    private static Set<Class<?>> entityDefinitions(EntityManager entityManager) {
        return entityManager.getMetamodel().getEntities().stream()
                .map(Type::getJavaType)
                .collect(Collectors.toSet());
    }
}
