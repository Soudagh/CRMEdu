package org.example.crmedu.infrastructure.repository.tutor;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.infrastructure.mapping.TutorEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link TutorRepository} interface using JPA. Provides methods for managing {@link Tutor} entities in the database.
 */
@Component
@RequiredArgsConstructor
public class TutorRepositoryImpl implements TutorRepository {

  private final DataTutorRepository tutorRepository;

  private final TutorEntityMapper mapper;

  @Override
  public Tutor save(Tutor tutor) {
    var requestedTutorEntity = mapper.toTutorEntity(tutor);
    var responsedTutorEntity = tutorRepository.save(requestedTutorEntity);
    return mapper.toTutor(responsedTutorEntity);
  }

  @Override
  public Optional<Tutor> findById(Long id) {
    return tutorRepository.findById(id).map(mapper::toTutor);
  }

  @Override
  public Page<Tutor> findAll(int pageNumber, int pageSize) {
    var page = tutorRepository.findAll(
        PageRequest.of(
            pageNumber,
            pageSize
        )
    ).map(mapper::toTutor);
    return new Page<Tutor>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  @Override
  public boolean existsByUser(Tutor tutor) {
    return tutorRepository.existsByUserId(tutor.getUser().getId());
  }

  @Override
  public void update(Tutor tutor) {
    tutorRepository.save(mapper.toTutorEntity(tutor));
  }

  @Override
  public void delete(Long id) {
    tutorRepository.deleteById(id);
  }
}
