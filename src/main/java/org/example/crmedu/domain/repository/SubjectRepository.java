package org.example.crmedu.domain.repository;

import java.util.Optional;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;

public interface SubjectRepository {

  Subject save(Subject subject);

  Optional<Subject> findById(Long id);

  boolean existsByName(Subject subject);

  Page<Subject> findAll(int pageNumber, int pageSize);

  void update(Subject subject);

  void delete(Long id);
}
