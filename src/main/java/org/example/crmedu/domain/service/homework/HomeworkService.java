package org.example.crmedu.domain.service.homework;

import java.util.List;
import org.example.crmedu.domain.enums.CompletedTaskStatus;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.domain.model.User;

public interface HomeworkService {

  Homework createDraft(Homework homework, User currentUser);

  Homework findById(Long id);

  Homework findByIdWithAccessCheck(Long id, User currentUser);

  void updateDraft(Homework homework, Long id, User currentUser);

  void deleteDraft(Long id, User currentUser);

  Homework publish(Long id, User currentUser);

  List<Homework> getHomeworkForStudent(Long userId);

  List<Homework> getTutorHomeworks(Long userId);

  List<CompletedTask> getSubmissionsForReview(Long homeworkId, User currentUser);

  CompletedTask submitAnswer(Long homeworkId, Long userId, String answer, List<String> attachmentUrls);

  CheckedTask gradeSubmission(Long completedTaskId, User currentUser, CompletedTaskStatus status, String comments);

  CompletedTask getStudentSubmission(Long homeworkId, Long userId);
}