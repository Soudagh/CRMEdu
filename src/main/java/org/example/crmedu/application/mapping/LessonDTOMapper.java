package org.example.crmedu.application.mapping;

import java.util.List;
import org.example.crmedu.application.dto.response.lesson.CreateLessonQrResponse;
import org.example.crmedu.application.dto.response.lesson.GetLessonResponse;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.LessonQr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonDTOMapper {

  @Mapping(source = "subjectProgram.subject", target = "subject")
  @Mapping(source = "subjectProgram.program", target = "program")
  GetLessonResponse getLessonResponse(Lesson lesson);

  List<GetLessonResponse> getLessonResponse(List<Lesson> lessons);

  CreateLessonQrResponse lessonQrToResponse(LessonQr qr);
}
