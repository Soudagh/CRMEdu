package org.example.crmedu.infrastructure.repository.schedule;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;
import org.example.crmedu.domain.repository.TutorScheduleRepository;
import org.example.crmedu.infrastructure.mapping.TutorScheduleEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TutorScheduleRepositoryImpl implements TutorScheduleRepository {

  private final DataTutorScheduleRepository tutorScheduleRepository;

  private final TutorScheduleEntityMapper mapper;


  @Override
  public TutorSchedule save(TutorSchedule schedule) {
    var requestedEntity = mapper.toTutorScheduleEntity(schedule);
    var responsedEntity = tutorScheduleRepository.save(requestedEntity);
    return mapper.toTutorSchedule(responsedEntity);
  }

  @Override
  public Optional<TutorSchedule> findById(Long id) {
    return tutorScheduleRepository.findById(id)
        .map(mapper::toTutorSchedule);
  }

  @Override
  public Page<TutorSchedule> findPagesByTutorId(int pageNumber, int pageSize, Long tutorId) {
    var page = tutorScheduleRepository.findTutorScheduleEntitiesByTutor_Id(
            tutorId,
            PageRequest.of(pageNumber, pageSize)
        )
        .map(mapper::toTutorSchedule);
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


  @Override
  public void update(TutorSchedule schedule) {
    tutorScheduleRepository.save(mapper.toTutorScheduleEntity(schedule));
  }

  @Override
  public void delete(Long id) {
    tutorScheduleRepository.deleteById(id);
  }
}
