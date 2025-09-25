package org.example.crmedu.infrastructure.mapping;

public interface BaseEntityMapper<D, E> {

  D toDomain(E entity);

  E toEntity(D domain);
}
