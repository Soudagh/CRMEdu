package org.example.crmedu.infrastructure.repository.tutor;

import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.repository.TutorRepository;
import org.example.crmedu.infrastructure.entity.TutorEntity;
import org.example.crmedu.infrastructure.mapping.TutorEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link TutorRepository} interface using JPA. Provides methods for managing {@link Tutor} entities in the database.
 */
@Component
public class TutorRepositoryImpl extends BaseRepository<Tutor, TutorEntity, Long> implements TutorRepository {

  private final DataTutorRepository tutorRepository;

  private final TutorEntityMapper tutorEntityMapper;


  public TutorRepositoryImpl(DataTutorRepository tutorRepository,
      TutorEntityMapper mapper) {
    super(tutorRepository, mapper);
    this.tutorRepository = tutorRepository;
    this.tutorEntityMapper = mapper;
  }

  @Override
  public boolean existsByUser(Tutor tutor) {
    return tutorRepository.existsByUserIdAndIdIsNot(tutor.getUser().getId(), tutor.getId());
  }

  @Override
  public Tutor findTutorByUserId(Long userId) {
    return tutorEntityMapper.toDomain(tutorRepository.findByUser_Id(userId));
  }

}
