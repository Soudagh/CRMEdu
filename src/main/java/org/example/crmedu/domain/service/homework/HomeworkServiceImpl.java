package org.example.crmedu.domain.service.homework;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.enums.CompletedTaskStatus;
import org.example.crmedu.domain.enums.HomeworkStatus;
import org.example.crmedu.domain.enums.NotificationType;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.SubscriptionStatus;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.OperationNotAllowedException;
import org.example.crmedu.domain.exception.ResourceAccessDeniedException;
import org.example.crmedu.domain.model.Attachment;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Notification;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.model.Subscription;
import org.example.crmedu.domain.model.Task;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.AttachmentRepository;
import org.example.crmedu.domain.repository.CheckedTaskRepository;
import org.example.crmedu.domain.repository.CompletedTaskRepository;
import org.example.crmedu.domain.repository.HomeworkRepository;
import org.example.crmedu.domain.repository.LessonRepository;
import org.example.crmedu.domain.repository.NotificationRepository;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.domain.repository.TaskRepository;
import org.example.crmedu.domain.service.student.StudentService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

  private final HomeworkRepository homeworkRepository;
  private final TaskRepository taskRepository;
  private final CompletedTaskRepository completedTaskRepository;
  private final CheckedTaskRepository checkedTaskRepository;
  private final AttachmentRepository attachmentRepository;
  private final NotificationRepository notificationRepository;
  private final LessonRepository lessonRepository;
  private final StudentRepository studentRepository;
  private final StudentService studentService;
  private final TutorService tutorService;

  @Override
  @Transactional
  public Homework createDraft(Homework homework, User currentUser) {
    verifyTutorOwnsLesson(currentUser, homework);

    homework.setStatus(HomeworkStatus.DRAFT);
    if (homework.getRightAnswer() == null) {
      homework.setRightAnswer("");
    }
    var created = homeworkRepository.create(homework);
    var task = new Task()
        .setHomework(created)
        .setTitle(created.getTitle())
        .setDescription(created.getDescription())
        .setRightAnswer(created.getRightAnswer())
        .setStartDate(created.getStartDate())
        .setEndDate(created.getEndDate());
    taskRepository.create(task);
    return created;
  }

  @Override
  @Transactional
  public Homework findById(Long id) {
    return homeworkRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Homework.class, id));
  }

  @Override
  @Transactional
  public Homework findByIdWithAccessCheck(Long id, User currentUser) {
    var homework = findById(id);
    var role = currentUser.getRole();

    if (role == Role.SUPERUSER || role == Role.ORG_ADMIN) {
      return homework;
    }

    if (role == Role.TUTOR) {
      verifyTutorOwnsHomework(currentUser, homework);
      return homework;
    }

    if (homework.getStatus() != HomeworkStatus.PUBLISHED) {
      throw new ResourceAccessDeniedException("Homework is not published");
    }

    if (role == Role.STUDENT) {
      var student = studentService.getStudentByUserId(currentUser.getId());
      verifyStudentHasAccessToHomework(student, homework);
    }

    return homework;
  }

  @Override
  @Transactional
  public void updateDraft(Homework homework, Long id, User currentUser) {
    var existing = findById(id);
    verifyTutorOwnsHomework(currentUser, existing);

    if (existing.getStatus() != HomeworkStatus.DRAFT) {
      throw new OperationNotAllowedException("Only draft homework can be edited");
    }
    if (homework.getRightAnswer() == null) {
      homework.setRightAnswer("");
    }
    homework.setId(id);
    homework.setLesson(existing.getLesson());
    homework.setStatus(HomeworkStatus.DRAFT);
    homeworkRepository.update(homework);
  }

  @Override
  @Transactional
  public void deleteDraft(Long id, User currentUser) {
    var existing = findById(id);
    verifyTutorOwnsHomework(currentUser, existing);

    if (existing.getStatus() != HomeworkStatus.DRAFT) {
      throw new OperationNotAllowedException("Only draft homework can be deleted");
    }
    homeworkRepository.delete(id);
  }

  @Override
  @Transactional
  public Homework publish(Long id, User currentUser) {
    var homework = findById(id);
    verifyTutorOwnsHomework(currentUser, homework);

    if (homework.getStatus() != HomeworkStatus.DRAFT) {
      throw new OperationNotAllowedException("Only draft homework can be published");
    }
    homework.setStatus(HomeworkStatus.PUBLISHED);
    homeworkRepository.update(homework);

    try {
      notifyStudentsAboutHomework(homework);
    } catch (Exception ignored) {
    }

    return homework;
  }

  @Override
  @Transactional
  public List<Homework> getTutorHomeworks(Long userId) {
    var tutor = tutorService.getTutorByUserId(userId);
    if (tutor == null) {
      throw new EntityNotFoundException(Tutor.class, userId);
    }
    return homeworkRepository.findByTutorId(tutor.getId());
  }

  @Override
  @Transactional
  public List<Homework> getHomeworkForStudent(Long userId) {
    var student = studentService.getStudentByUserId(userId);
    var lessonIds = student.getSubscriptions().stream()
        .filter(s -> s.getSubscriptionStatus() == SubscriptionStatus.ACTIVE)
        .map(Subscription::getProgram)
        .flatMap(program -> lessonRepository.getLessonsByProgramId(program.getId()).stream())
        .map(Lesson::getId)
        .toList();

    if (lessonIds.isEmpty()) {
      return List.of();
    }
    return homeworkRepository.findPublishedByLessonIds(lessonIds);
  }

  @Override
  @Transactional
  public List<CompletedTask> getSubmissionsForReview(Long homeworkId, User currentUser) {
    var homework = findById(homeworkId);

    if (!isAdmin(currentUser)) {
      var tutor = tutorService.getTutorByUserId(currentUser.getId());
      if (homework.getLesson() != null && homework.getLesson().getTutor() != null
          && !homework.getLesson().getTutor().getId().equals(tutor.getId())) {
        throw new ResourceAccessDeniedException("Tutor does not have access to this homework's submissions");
      }
    }

    return completedTaskRepository.findByHomeworkId(homeworkId);
  }

  @Override
  @Transactional
  public CompletedTask submitAnswer(Long homeworkId, Long userId, String answer, List<String> attachmentUrls) {
    var homework = findById(homeworkId);
    if (homework.getStatus() != HomeworkStatus.PUBLISHED) {
      throw new OperationNotAllowedException("Cannot submit answer to unpublished homework");
    }

    var student = studentService.getStudentByUserId(userId);
    verifyStudentHasAccessToHomework(student, homework);

    var tasks = taskRepository.findByHomeworkId(homeworkId);
    if (tasks.isEmpty()) {
      throw new OperationNotAllowedException("Homework has no tasks");
    }

    var task = tasks.get(0);

    var existing = completedTaskRepository.findByTaskIdAndStudentId(task.getId(), student.getId());
    if (existing.isPresent()) {
      throw new OperationNotAllowedException("Student already submitted an answer for this homework");
    }

    var completedTask = new CompletedTask()
        .setTask(task)
        .setStudent(student)
        .setTitle(homework.getTitle())
        .setStudentAnswer(answer)
        .setCompletedTaskStatus(CompletedTaskStatus.NO_ANSWER)
        .setDueDate(homework.getEndDate() != null ? homework.getEndDate() : ZonedDateTime.now().plusDays(7));

    var saved = completedTaskRepository.create(completedTask);

    if (attachmentUrls != null) {
      for (String url : attachmentUrls) {
        var attachment = new Attachment()
            .setCompletedTask(saved)
            .setUrl(url);
        attachmentRepository.create(attachment);
      }
    }

    return saved;
  }

  @Override
  @Transactional
  public CheckedTask gradeSubmission(Long completedTaskId, User currentUser, CompletedTaskStatus status,
      String comments) {
    var completedTask = completedTaskRepository.findById(completedTaskId)
        .orElseThrow(() -> new EntityNotFoundException(CompletedTask.class, completedTaskId));

    Tutor tutor;
    if (isAdmin(currentUser)) {
      // Admins can grade without being the lesson's tutor; use any available tutor context
      tutor = null;
    } else {
      tutor = tutorService.getTutorByUserId(currentUser.getId());
      verifyTutorHasAccessToSubmission(tutor, completedTask);
    }

    completedTask.setCompletedTaskStatus(status);
    completedTaskRepository.update(completedTask);

    var existingCheck = checkedTaskRepository.findByCompletedTaskId(completedTaskId);
    CheckedTask checkedTask;
    if (existingCheck.isPresent()) {
      checkedTask = existingCheck.get();
      checkedTask.setComments(comments);
      checkedTask.setIsChecked(true);
      checkedTaskRepository.update(checkedTask);
    } else {
      checkedTask = new CheckedTask()
          .setCompletedTask(completedTask)
          .setTutor(tutor)
          .setComments(comments)
          .setIsChecked(true)
          .setInspectionDate(ZonedDateTime.now());
      checkedTask = checkedTaskRepository.create(checkedTask);
    }

    try {
      if (completedTask.getStudent() != null && completedTask.getStudent().getUser() != null) {
        var notification = new Notification()
            .setUser(completedTask.getStudent().getUser())
            .setTitle("Домашнее задание проверено")
            .setDescription("Ваше задание \"" + completedTask.getTitle() + "\" было проверено преподавателем")
            .setNotificationType(NotificationType.HOMEWORK)
            .setCreatedAt(ZonedDateTime.now())
            .setIsRead(false);
        notificationRepository.create(notification);
      }
    } catch (Exception ignored) {
    }

    return checkedTask;
  }

  @Override
  @Transactional
  public CompletedTask getStudentSubmission(Long homeworkId, Long userId) {
    var student = studentService.getStudentByUserId(userId);
    var tasks = taskRepository.findByHomeworkId(homeworkId);
    if (tasks.isEmpty()) {
      throw new EntityNotFoundException(CompletedTask.class, homeworkId);
    }
    return completedTaskRepository.findByTaskIdAndStudentId(tasks.get(0).getId(), student.getId())
        .orElseThrow(() -> new EntityNotFoundException(CompletedTask.class, homeworkId));
  }

  // --- Access verification helpers ---

  private boolean isAdmin(User user) {
    return user.getRole() == Role.SUPERUSER || user.getRole() == Role.ORG_ADMIN;
  }

  private void verifyTutorOwnsLesson(User currentUser, Homework homework) {
    if (isAdmin(currentUser) || homework.getLesson() == null) {
      return;
    }
    var lesson = lessonRepository.findById(homework.getLesson().getId())
        .orElseThrow(() -> new EntityNotFoundException(Lesson.class, homework.getLesson().getId()));
    if (lesson.getTutor() == null || lesson.getTutor().getUser() == null) {
      return;
    }
    if (!lesson.getTutor().getUser().getId().equals(currentUser.getId())) {
      throw new ResourceAccessDeniedException("Tutor does not own the lesson for this homework");
    }
  }

  private void verifyTutorOwnsHomework(User currentUser, Homework homework) {
    if (isAdmin(currentUser)) {
      return;
    }
    if (homework.getLesson() == null || homework.getLesson().getTutor() == null
        || homework.getLesson().getTutor().getUser() == null) {
      return;
    }
    if (!homework.getLesson().getTutor().getUser().getId().equals(currentUser.getId())) {
      throw new ResourceAccessDeniedException("Tutor does not have access to this homework");
    }
  }

  private void verifyStudentHasAccessToHomework(Student student, Homework homework) {
    if (homework.getLesson() == null || homework.getLesson().getSubjectProgram() == null) {
      return;
    }
    var programId = homework.getLesson().getSubjectProgram().getProgram().getId();
    boolean hasAccess = student.getSubscriptions().stream()
        .anyMatch(s -> s.getSubscriptionStatus() == SubscriptionStatus.ACTIVE
            && s.getProgram().getId().equals(programId));
    if (!hasAccess) {
      throw new ResourceAccessDeniedException("Student does not have access to this homework");
    }
  }

  private void verifyTutorHasAccessToSubmission(Tutor tutor, CompletedTask completedTask) {
    if (completedTask.getTask() == null || completedTask.getTask().getHomework() == null) {
      return;
    }
    var homework = completedTask.getTask().getHomework();
    if (homework.getLesson() == null || homework.getLesson().getTutor() == null) {
      return;
    }
    if (!homework.getLesson().getTutor().getId().equals(tutor.getId())) {
      throw new ResourceAccessDeniedException("Tutor does not have access to grade this submission");
    }
  }

  private void notifyStudentsAboutHomework(Homework homework) {
    if (homework.getLesson() == null || homework.getLesson().getSubjectProgram() == null) {
      return;
    }
    var programId = homework.getLesson().getSubjectProgram().getProgram().getId();
    var students = studentRepository.findByProgramId(programId);

    for (Student student : students) {
      if (student.getUser() == null) {
        continue;
      }
      var notification = new Notification()
          .setUser(student.getUser())
          .setTitle("Новое домашнее задание")
          .setDescription("Опубликовано задание: " + homework.getTitle())
          .setNotificationType(NotificationType.HOMEWORK)
          .setCreatedAt(ZonedDateTime.now())
          .setIsRead(false);
      notificationRepository.create(notification);
    }
  }
}