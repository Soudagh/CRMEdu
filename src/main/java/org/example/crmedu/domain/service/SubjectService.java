package org.example.crmedu.domain.service;

import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;

public interface SubjectService {

  Subject create(Subject subject);

  Subject findById(Long id);

  Page<Subject> findAll(int pageNumber, int pageSize);

  void update(Subject subject, Long id);

  void delete(Long id);
}
