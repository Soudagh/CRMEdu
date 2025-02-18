package org.example.crmedu.application.mapping;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.schedule.CreateTutorScheduleRequest;
import org.example.crmedu.application.dto.request.schedule.UpdateTutorScheduleRequest;
import org.example.crmedu.application.dto.response.schedule.CreateTutorScheduleResponse;
import org.example.crmedu.application.dto.response.schedule.GetTutorScheduleResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.TutorSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper interface for converting between TutorSchedule domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = TutorDTOMapper.class)
public interface TutorScheduleDTOMapper {

  /**
   * Converts a {@link CreateTutorScheduleRequest} DTO to a {@link TutorSchedule} model.
   *
   * @param request the DTO containing tutor schedule creation details
   * @return the corresponding {@link TutorSchedule} model
   */
  @Mapping(target = "timeStart", source = "timeStart")
  @Mapping(target = "timeEnd", source = "timeEnd")
  TutorSchedule createTutorScheduleRequestToTutorSchedule(CreateTutorScheduleRequest request);

  /**
   * Converts a {@link UpdateTutorScheduleRequest} DTO to a corresponding {@link TutorSchedule} DTO.
   *
   * @param request the DTO containing tutor schedule update details
   * @return the corresponding {@link TutorSchedule} model
   */
  @Mapping(target = "timeStart", source = "timeStart")
  @Mapping(target = "timeEnd", source = "timeEnd")
  TutorSchedule updateTutorScheduleRequestToTutorSchedule(UpdateTutorScheduleRequest request);

  /**
   * Converts a {@link TutorSchedule} domain model to {@link CreateTutorScheduleResponse} DTO.
   *
   * @param tutorSchedule the created tutor schedule model
   * @return the corresponding {@link CreateTutorScheduleResponse} DTO.
   */
  @Mapping(target = "timeStart", source = "timeStart")
  @Mapping(target = "timeEnd", source = "timeEnd")
  @Mapping(target = "tutorId", source = "tutor", qualifiedByName = "tutorToId")
  CreateTutorScheduleResponse tutorScheduleToCreateTutorResponse(TutorSchedule tutorSchedule);

  /**
   * Converts a {@link TutorSchedule} domain model to {@link GetTutorScheduleResponse} DTO.
   *
   * @param tutorSchedule tutor schedule model to convert
   * @return the corresponding {@link GetTutorScheduleResponse} DTO.
   */
  @Mapping(target = "timeStart", source = "timeStart")
  @Mapping(target = "timeEnd", source = "timeEnd")
  GetTutorScheduleResponse tutorScheduleToGetTutorScheduleResponse(TutorSchedule tutorSchedule);

  /**
   * Converts a paginated {@link Page} of {@link TutorSchedule} entities to a paginated {@link PageDTO} of {@link GetTutorScheduleResponse} DTOs.
   *
   * @param page the paginated tutor schedule entities
   * @return a paginated response DTO containing tutor schedules data
   */
  PageDTO<GetTutorScheduleResponse> pageTutorScheduleToPageDTOGetTutorSchedule(Page<TutorSchedule> page);

  /**
   * Converts time presented in {@link String} format to {@link OffsetTime}
   *
   * @param time time in {@link String} format
   * @return converted time to {@link OffsetTime} format
   */
  default OffsetTime stringToOffsetTime(String time) {
    return time == null ? null : OffsetTime.parse(time, DateTimeFormatter.ISO_OFFSET_TIME)
        .withOffsetSameInstant(ZoneOffset.UTC);
  }

  /**
   * Converts time presented in {@link OffsetTime} format to {@link String} format.
   *
   * @param time time in {@link OffsetTime} format
   * @return converted time to {@link String}
   */
  default String offsetTimeToString(OffsetTime time) {
    return time == null ? null : time.format(DateTimeFormatter.ISO_OFFSET_TIME);
  }
}
