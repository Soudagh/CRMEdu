package org.example.crmedu.infrastructure.repository.schedule;

import java.util.Set;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.repository.TutorScheduleRepository;
import org.example.crmedu.infrastructure.entity.TutorScheduleEntity;
import org.example.crmedu.infrastructure.mapping.TutorScheduleEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class TutorScheduleRepositoryImpl extends BaseRepository<TutorSchedule, TutorScheduleEntity, Long> implements TutorScheduleRepository {

  private final DataTutorScheduleRepository tutorScheduleRepository;

  private final TutorScheduleEntityMapper mapper;

  public TutorScheduleRepositoryImpl(
      DataTutorScheduleRepository tutorScheduleRepository,
      TutorScheduleEntityMapper mapper
  ) {
    super(tutorScheduleRepository, mapper);
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.mapper = mapper;
  }

  @Override
  public Page<TutorSchedule> findPagesByTutorId(int pageNumber, int pageSize, Long tutorId) {
    var page = tutorScheduleRepository.findTutorScheduleEntitiesByTutor_Id(
            tutorId,
            PageRequest.of(pageNumber, pageSize)
        )
        .map(mapper::toDomain);
    return new Page<TutorSchedule>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalCount(page.getTotalElements())
        .setTotalPages(page.getTotalPages());
  }

  @Override
  public Set<TutorSchedule> findByTutorId(Long tutorId) {
    return mapper.setTutorScheduleEntityToSetTutorSchedule(tutorScheduleRepository.findTutorScheduleEntitiesByTutor_Id(tutorId));
  }
}
