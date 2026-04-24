package org.example.crmedu.application.mapping;

import java.util.List;
import org.example.crmedu.application.dto.request.homework.CreateHomeworkRequest;
import org.example.crmedu.application.dto.request.homework.UpdateHomeworkRequest;
import org.example.crmedu.application.dto.response.homework.GetCheckedTaskResponse;
import org.example.crmedu.application.dto.response.homework.GetCompletedTaskResponse;
import org.example.crmedu.application.dto.response.homework.GetHomeworkResponse;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.domain.model.Homework;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HomeworkDTOMapper {

  @Mapping(target = "lesson.id", source = "lessonId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  Homework createRequestToHomework(CreateHomeworkRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lesson", ignore = true)
  @Mapping(target = "status", ignore = true)
  Homework updateRequestToHomework(UpdateHomeworkRequest request);

  @Mapping(target = "lessonId", source = "lesson.id")
  GetHomeworkResponse homeworkToGetResponse(Homework homework);

  List<GetHomeworkResponse> homeworksToGetResponses(List<Homework> homeworks);

  @Mapping(target = "homeworkId", source = "task.homework.id")
  @Mapping(target = "studentId", source = "student.id")
  @Mapping(target = "studentName", source = "student.user.name")
  @Mapping(target = "status", source = "completedTaskStatus")
  @Mapping(target = "attachmentUrls", ignore = true)
  GetCompletedTaskResponse completedTaskToGetResponse(CompletedTask completedTask);

  List<GetCompletedTaskResponse> completedTasksToGetResponses(List<CompletedTask> tasks);

  @Mapping(target = "completedTaskId", source = "completedTask.id")
  @Mapping(target = "tutorId", source = "tutor.id")
  @Mapping(target = "tutorName", source = "tutor.user.name")
  GetCheckedTaskResponse checkedTaskToGetResponse(CheckedTask checkedTask);
}