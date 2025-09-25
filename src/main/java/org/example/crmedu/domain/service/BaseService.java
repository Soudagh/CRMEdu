package org.example.crmedu.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.repository.BaseCrudRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class BaseService<T> {

  private final BaseCrudRepository<T> baseCrudRepository;

  private final Class<T> clazz;

  @Transactional
  public T findById(Long id) {
    return baseCrudRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(clazz, id));
  }

  public Page<T> findAll(int pageNumber, int pageSize) {
    return baseCrudRepository.findAll(pageNumber, pageSize);
  }

  public void delete(Long id) {
    baseCrudRepository.delete(id);
  }
}
