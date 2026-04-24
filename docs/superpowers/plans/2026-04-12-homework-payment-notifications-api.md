# Homework, Payment & Notifications API Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement production-ready REST API for homework assignments, read-only payment API, and usable notification endpoints for the CRMEdu online school backend.

**Architecture:** Follow existing 3-layer pattern (application/domain/infrastructure). New entities follow the same BaseCrudRepository/BaseService/MapStruct pattern. Homework operates at the `homework` table level with a new `status` field (DRAFT/PUBLISHED). Payments get a new `payment` table with read-only API. Notifications get a real repository-backed service and DTO responses.

**Tech Stack:** Spring Boot 3.4.2, Java 21, PostgreSQL, Liquibase, MapStruct, Lombok, Spring Security (@Secured), JPA/Hibernate

---

## File Structure

### New files to create:

**Liquibase migrations:**
- `src/main/resources/db/changelog/1.0.0/changes/026-alter-homework-add-status.yaml`
- `src/main/resources/db/changelog/1.0.0/changes/027-alter-completed-task-add-student-id.yaml`
- `src/main/resources/db/changelog/1.0.0/changes/028-alter-checked-task-add-inspection-date.yaml`
- `src/main/resources/db/changelog/1.0.0/changes/029-create-payment-table.yaml`

**Domain enums:**
- `src/main/java/org/example/crmedu/domain/enums/HomeworkStatus.java`
- `src/main/java/org/example/crmedu/domain/enums/PaymentType.java`

**Domain models:**
- `src/main/java/org/example/crmedu/domain/model/Homework.java`
- `src/main/java/org/example/crmedu/domain/model/Payment.java`

**Domain repositories:**
- `src/main/java/org/example/crmedu/domain/repository/HomeworkRepository.java`
- `src/main/java/org/example/crmedu/domain/repository/CompletedTaskRepository.java`
- `src/main/java/org/example/crmedu/domain/repository/CheckedTaskRepository.java`
- `src/main/java/org/example/crmedu/domain/repository/AttachmentRepository.java`
- `src/main/java/org/example/crmedu/domain/repository/NotificationRepository.java`
- `src/main/java/org/example/crmedu/domain/repository/PaymentRepository.java`

**Domain services:**
- `src/main/java/org/example/crmedu/domain/service/homework/HomeworkService.java`
- `src/main/java/org/example/crmedu/domain/service/homework/HomeworkServiceImpl.java`
- `src/main/java/org/example/crmedu/domain/service/payment/PaymentService.java`
- `src/main/java/org/example/crmedu/domain/service/payment/PaymentServiceImpl.java`

**Infrastructure entity mappers:**
- `src/main/java/org/example/crmedu/infrastructure/mapping/HomeworkEntityMapper.java`
- `src/main/java/org/example/crmedu/infrastructure/mapping/CompletedTaskEntityMapper.java`
- `src/main/java/org/example/crmedu/infrastructure/mapping/CheckedTaskEntityMapper.java`
- `src/main/java/org/example/crmedu/infrastructure/mapping/AttachmentEntityMapper.java`
- `src/main/java/org/example/crmedu/infrastructure/mapping/PaymentEntityMapper.java`

**Infrastructure repositories:**
- `src/main/java/org/example/crmedu/infrastructure/repository/homework/DataHomeworkRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/homework/HomeworkRepositoryImpl.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/completedtask/DataCompletedTaskRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/completedtask/CompletedTaskRepositoryImpl.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/checkedtask/DataCheckedTaskRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/checkedtask/CheckedTaskRepositoryImpl.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/attachment/DataAttachmentRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/attachment/AttachmentRepositoryImpl.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/notification/DataNotificationRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/notification/NotificationRepositoryImpl.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/payment/DataPaymentRepository.java`
- `src/main/java/org/example/crmedu/infrastructure/repository/payment/PaymentRepositoryImpl.java`

**Infrastructure entities (new):**
- `src/main/java/org/example/crmedu/infrastructure/entity/PaymentEntity.java`

**Application DTOs:**
- `src/main/java/org/example/crmedu/application/dto/request/homework/CreateHomeworkRequest.java`
- `src/main/java/org/example/crmedu/application/dto/request/homework/UpdateHomeworkRequest.java`
- `src/main/java/org/example/crmedu/application/dto/request/homework/SubmitAnswerRequest.java`
- `src/main/java/org/example/crmedu/application/dto/request/homework/GradeHomeworkRequest.java`
- `src/main/java/org/example/crmedu/application/dto/response/homework/GetHomeworkResponse.java`
- `src/main/java/org/example/crmedu/application/dto/response/homework/GetCompletedTaskResponse.java`
- `src/main/java/org/example/crmedu/application/dto/response/homework/GetCheckedTaskResponse.java`
- `src/main/java/org/example/crmedu/application/dto/response/payment/GetPaymentResponse.java`
- `src/main/java/org/example/crmedu/application/dto/response/payment/GetBalanceResponse.java`
- `src/main/java/org/example/crmedu/application/dto/response/notification/GetNotificationResponse.java`

**Application DTO mappers:**
- `src/main/java/org/example/crmedu/application/mapping/HomeworkDTOMapper.java`
- `src/main/java/org/example/crmedu/application/mapping/PaymentDTOMapper.java`
- `src/main/java/org/example/crmedu/application/mapping/NotificationDTOMapper.java`

**Application controllers:**
- `src/main/java/org/example/crmedu/application/controller/HomeworkController.java`
- `src/main/java/org/example/crmedu/application/controller/PaymentController.java`

### Existing files to modify:

- `src/main/resources/db/changelog/1.0.0/1.0.0-changelog.yaml` — add new migration includes
- `src/main/java/org/example/crmedu/infrastructure/entity/HomeworkEntity.java` — add status field
- `src/main/java/org/example/crmedu/infrastructure/entity/CompletedTaskEntity.java` — add student, dueDate, title fields
- `src/main/java/org/example/crmedu/infrastructure/entity/CheckedTaskEntity.java` — add completedTask relationship, inspection_date
- `src/main/java/org/example/crmedu/domain/model/CompletedTask.java` — add student field
- `src/main/java/org/example/crmedu/domain/model/CheckedTask.java` — add comments field
- `src/main/java/org/example/crmedu/domain/service/notification/NotificationService.java` — add create and markAsRead methods
- `src/main/java/org/example/crmedu/domain/service/notification/NotificationServiceImpl.java` — real implementation
- `src/main/java/org/example/crmedu/application/controller/UserController.java` — fix notifications endpoint to use DTO

---

## Task 1: Liquibase migrations

**Files:**
- Create: `src/main/resources/db/changelog/1.0.0/changes/026-alter-homework-add-status.yaml`
- Create: `src/main/resources/db/changelog/1.0.0/changes/027-alter-completed-task-add-student-id.yaml`
- Create: `src/main/resources/db/changelog/1.0.0/changes/028-alter-checked-task-add-inspection-date.yaml`
- Create: `src/main/resources/db/changelog/1.0.0/changes/029-create-payment-table.yaml`
- Modify: `src/main/resources/db/changelog/1.0.0/1.0.0-changelog.yaml`

- [ ] **Step 1: Create migration 026 — add status column to homework table**

```yaml
databaseChangeLog:
  - changeSet:
      id: 026-alter-homework-add-status
      author: soudagh
      changes:
        - addColumn:
            tableName: homework
            schemaName: crmedu
            columns:
              - column:
                  name: status
                  type: varchar(20)
                  defaultValue: 'DRAFT'
                  constraints:
                    nullable: false
```

- [ ] **Step 2: Create migration 027 — add student_id to completed_task**

```yaml
databaseChangeLog:
  - changeSet:
      id: 027-alter-completed-task-add-student-id
      author: soudagh
      changes:
        - addColumn:
            tableName: completed_task
            schemaName: crmedu
            columns:
              - column:
                  name: student_id
                  type: bigint
                  constraints:
                    nullable: true
                    foreignKeyName: fk_completed_task_student_id_student
                    references: student(id)
```

- [ ] **Step 3: Create migration 028 — add inspection_date to checked_task**

The checked_task table is missing inspection_date column (entity has @UpdateTimestamp but column doesn't exist in DB schema).

```yaml
databaseChangeLog:
  - changeSet:
      id: 028-alter-checked-task-add-inspection-date
      author: soudagh
      changes:
        - addColumn:
            tableName: checked_task
            schemaName: crmedu
            columns:
              - column:
                  name: inspection_date
                  type: timestamp with time zone
```

- [ ] **Step 4: Create migration 029 — create payment table**

```yaml
databaseChangeLog:
  - changeSet:
      id: 029-create-payment-table
      author: soudagh
      changes:
        - createTable:
            tableName: payment
            schemaName: crmedu
            remarks: table for student payment history
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    nullable: false
                    primaryKey: true
                    primaryKeyName: pk_payment
              - column:
                  name: student_id
                  type: bigint
                  constraints:
                    nullable: false
                    foreignKeyName: fk_payment_student_id_student
                    references: student(id)
              - column:
                  name: amount
                  type: integer
                  constraints:
                    nullable: false
              - column:
                  name: description
                  type: text
              - column:
                  name: payment_type
                  type: varchar(30)
                  constraints:
                    nullable: false
              - column:
                  name: payment_date
                  type: timestamp with time zone
                  constraints:
                    nullable: false
              - column:
                  name: status
                  type: varchar(20)
                  constraints:
                    nullable: false
```

- [ ] **Step 5: Register all new migrations in master changelog**

Add to `1.0.0-changelog.yaml` after line 50 (the 025 entry):
```yaml
  - include:
      file: db/changelog/1.0.0/changes/026-alter-homework-add-status.yaml
  - include:
      file: db/changelog/1.0.0/changes/027-alter-completed-task-add-student-id.yaml
  - include:
      file: db/changelog/1.0.0/changes/028-alter-checked-task-add-inspection-date.yaml
  - include:
      file: db/changelog/1.0.0/changes/029-create-payment-table.yaml
```

- [ ] **Step 6: Commit**

```bash
git add src/main/resources/db/changelog/
git commit -m "feat: add liquibase migrations for homework status, completed_task student_id, checked_task inspection_date, and payment table"
```

---

## Task 2: Domain enums and models

**Files:**
- Create: `src/main/java/org/example/crmedu/domain/enums/HomeworkStatus.java`
- Create: `src/main/java/org/example/crmedu/domain/enums/PaymentType.java`
- Create: `src/main/java/org/example/crmedu/domain/model/Homework.java`
- Create: `src/main/java/org/example/crmedu/domain/model/Payment.java`
- Modify: `src/main/java/org/example/crmedu/domain/model/CompletedTask.java`
- Modify: `src/main/java/org/example/crmedu/domain/model/CheckedTask.java`

- [ ] **Step 1: Create HomeworkStatus enum**

```java
package org.example.crmedu.domain.enums;

public enum HomeworkStatus {
  DRAFT,
  PUBLISHED
}
```

- [ ] **Step 2: Create PaymentType enum**

```java
package org.example.crmedu.domain.enums;

public enum PaymentType {
  TOPUP,
  LESSON_CHARGE,
  REFUND
}
```

- [ ] **Step 3: Create Homework domain model**

```java
package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.HomeworkStatus;

@Data
@Accessors(chain = true)
public class Homework {
  private Long id;
  private Lesson lesson;
  private String title;
  private String description;
  private String rightAnswer;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private HomeworkStatus status;
}
```

- [ ] **Step 4: Create Payment domain model**

```java
package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.PaymentStatus;
import org.example.crmedu.domain.enums.PaymentType;

@Data
@Accessors(chain = true)
public class Payment {
  private Long id;
  private Student student;
  private Integer amount;
  private String description;
  private PaymentType paymentType;
  private ZonedDateTime paymentDate;
  private PaymentStatus status;
}
```

- [ ] **Step 5: Add student field to CompletedTask domain model**

Add to `CompletedTask.java`:
```java
private Student student;
```

- [ ] **Step 6: Add comments field to CheckedTask domain model**

Add to `CheckedTask.java`:
```java
private String comments;
```

- [ ] **Step 7: Commit**

```bash
git add src/main/java/org/example/crmedu/domain/
git commit -m "feat: add Homework and Payment domain models, HomeworkStatus and PaymentType enums, update CompletedTask and CheckedTask"
```

---

## Task 3: Fix existing JPA entities

**Files:**
- Modify: `src/main/java/org/example/crmedu/infrastructure/entity/HomeworkEntity.java`
- Modify: `src/main/java/org/example/crmedu/infrastructure/entity/CompletedTaskEntity.java`
- Modify: `src/main/java/org/example/crmedu/infrastructure/entity/CheckedTaskEntity.java`

- [ ] **Step 1: Add status field to HomeworkEntity**

Add to `HomeworkEntity.java` after endDate field:
```java
@Enumerated(EnumType.STRING)
@Column(name = "status", nullable = false)
private HomeworkStatus status = HomeworkStatus.DRAFT;
```
Import: `org.example.crmedu.domain.enums.HomeworkStatus`, `jakarta.persistence.EnumType`, `jakarta.persistence.Enumerated`

- [ ] **Step 2: Fix CompletedTaskEntity — add student, dueDate, title**

Add to `CompletedTaskEntity.java`:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "student_id")
private StudentEntity studentEntity;

@Column(name = "title", nullable = false)
private String title;

@Column(name = "due_date", nullable = false)
private ZonedDateTime dueDate;
```
Import: `java.time.ZonedDateTime`

- [ ] **Step 3: Fix CheckedTaskEntity — add completedTask relationship**

Add to `CheckedTaskEntity.java`:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "completed_task_id")
private CompletedTaskEntity completedTaskEntity;
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/org/example/crmedu/infrastructure/entity/
git commit -m "feat: fix HomeworkEntity, CompletedTaskEntity, CheckedTaskEntity — add missing fields and relationships"
```

---

## Task 4: New JPA entity — PaymentEntity

**Files:**
- Create: `src/main/java/org/example/crmedu/infrastructure/entity/PaymentEntity.java`

- [ ] **Step 1: Create PaymentEntity**

```java
package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.PaymentStatus;
import org.example.crmedu.domain.enums.PaymentType;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "payment", schema = "crmedu")
public class PaymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private StudentEntity studentEntity;

  @Column(name = "amount", nullable = false)
  private Integer amount;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type", nullable = false)
  private PaymentType paymentType;

  @Column(name = "payment_date", nullable = false)
  private ZonedDateTime paymentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private PaymentStatus status;
}
```

- [ ] **Step 2: Commit**

```bash
git add src/main/java/org/example/crmedu/infrastructure/entity/PaymentEntity.java
git commit -m "feat: add PaymentEntity"
```

---

## Task 5: Entity mappers

**Files:**
- Create: `src/main/java/org/example/crmedu/infrastructure/mapping/HomeworkEntityMapper.java`
- Create: `src/main/java/org/example/crmedu/infrastructure/mapping/CompletedTaskEntityMapper.java`
- Create: `src/main/java/org/example/crmedu/infrastructure/mapping/CheckedTaskEntityMapper.java`
- Create: `src/main/java/org/example/crmedu/infrastructure/mapping/AttachmentEntityMapper.java`
- Create: `src/main/java/org/example/crmedu/infrastructure/mapping/PaymentEntityMapper.java`

- [ ] **Step 1: Create HomeworkEntityMapper**

```java
package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LessonEntityMapper.class)
public interface HomeworkEntityMapper extends BaseEntityMapper<Homework, HomeworkEntity> {

  @Override
  @Mapping(source = "lessonEntity", target = "lesson")
  Homework toDomain(HomeworkEntity entity);

  @Override
  @Mapping(source = "lesson", target = "lessonEntity")
  HomeworkEntity toEntity(Homework domain);

  List<Homework> toDomain(List<HomeworkEntity> entities);
}
```

- [ ] **Step 2: Create CompletedTaskEntityMapper**

```java
package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.CompletedTask;
import org.example.crmedu.infrastructure.entity.CompletedTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentEntityMapper.class})
public interface CompletedTaskEntityMapper extends BaseEntityMapper<CompletedTask, CompletedTaskEntity> {

  @Override
  @Mapping(source = "taskEntity", target = "task")
  @Mapping(source = "studentEntity", target = "student")
  CompletedTask toDomain(CompletedTaskEntity entity);

  @Override
  @Mapping(source = "task", target = "taskEntity")
  @Mapping(source = "student", target = "studentEntity")
  CompletedTaskEntity toEntity(CompletedTask domain);

  List<CompletedTask> toDomain(List<CompletedTaskEntity> entities);
}
```

Note: The `task` in `CompletedTask` maps to `Task` domain model, but `CompletedTaskEntity.taskEntity` is a `TaskEntity`. Since Task domain model has `Lesson lesson` but TaskEntity has `HomeworkEntity homeworkEntity`, we need a TaskEntityMapper too. However, for the MVP, MapStruct can handle simple field mapping. The key fields (id, title, description) will map correctly. We'll add a minimal TaskEntityMapper if needed.

- [ ] **Step 3: Create CheckedTaskEntityMapper**

```java
package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.CheckedTask;
import org.example.crmedu.infrastructure.entity.CheckedTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TutorEntityMapper.class, CompletedTaskEntityMapper.class})
public interface CheckedTaskEntityMapper extends BaseEntityMapper<CheckedTask, CheckedTaskEntity> {

  @Override
  @Mapping(source = "completedTaskEntity", target = "completedTask")
  @Mapping(source = "tutorEntity", target = "tutor")
  CheckedTask toDomain(CheckedTaskEntity entity);

  @Override
  @Mapping(source = "completedTask", target = "completedTaskEntity")
  @Mapping(source = "tutor", target = "tutorEntity")
  CheckedTaskEntity toEntity(CheckedTask domain);

  List<CheckedTask> toDomain(List<CheckedTaskEntity> entities);
}
```

- [ ] **Step 4: Create AttachmentEntityMapper**

```java
package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Attachment;
import org.example.crmedu.infrastructure.entity.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompletedTaskEntityMapper.class)
public interface AttachmentEntityMapper extends BaseEntityMapper<Attachment, AttachmentEntity> {

  @Override
  @Mapping(source = "completedTaskEntity", target = "completedTask")
  Attachment toDomain(AttachmentEntity entity);

  @Override
  @Mapping(source = "completedTask", target = "completedTaskEntity")
  AttachmentEntity toEntity(Attachment domain);

  List<Attachment> toDomain(List<AttachmentEntity> entities);
}
```

- [ ] **Step 5: Create PaymentEntityMapper**

```java
package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.infrastructure.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StudentEntityMapper.class)
public interface PaymentEntityMapper extends BaseEntityMapper<Payment, PaymentEntity> {

  @Override
  @Mapping(source = "studentEntity", target = "student")
  Payment toDomain(PaymentEntity entity);

  @Override
  @Mapping(source = "student", target = "studentEntity")
  PaymentEntity toEntity(Payment domain);

  List<Payment> toDomain(List<PaymentEntity> entities);
}
```

- [ ] **Step 6: Commit**

```bash
git add src/main/java/org/example/crmedu/infrastructure/mapping/
git commit -m "feat: add entity mappers for Homework, CompletedTask, CheckedTask, Attachment, Payment"
```

---

## Task 6: Domain repositories

**Files:**
- Create: `src/main/java/org/example/crmedu/domain/repository/HomeworkRepository.java`
- Create: `src/main/java/org/example/crmedu/domain/repository/CompletedTaskRepository.java`
- Create: `src/main/java/org/example/crmedu/domain/repository/CheckedTaskRepository.java`
- Create: `src/main/java/org/example/crmedu/domain/repository/AttachmentRepository.java`
- Create: `src/main/java/org/example/crmedu/domain/repository/NotificationRepository.java`
- Create: `src/main/java/org/example/crmedu/domain/repository/PaymentRepository.java`

- [ ] **Step 1: Create HomeworkRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Homework;

public interface HomeworkRepository extends BaseCrudRepository<Homework> {
  List<Homework> findByLessonId(Long lessonId);
  List<Homework> findPublishedByLessonIds(List<Long> lessonIds);
}
```

- [ ] **Step 2: Create CompletedTaskRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CompletedTask;

public interface CompletedTaskRepository extends BaseCrudRepository<CompletedTask> {
  List<CompletedTask> findByHomeworkId(Long homeworkId);
  List<CompletedTask> findByStudentId(Long studentId);
  Optional<CompletedTask> findByTaskIdAndStudentId(Long taskId, Long studentId);
}
```

- [ ] **Step 3: Create CheckedTaskRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import java.util.Optional;
import org.example.crmedu.domain.model.CheckedTask;

public interface CheckedTaskRepository extends BaseCrudRepository<CheckedTask> {
  Optional<CheckedTask> findByCompletedTaskId(Long completedTaskId);
  List<CheckedTask> findByTutorId(Long tutorId);
  List<CheckedTask> findUncheckedByTutorId(Long tutorId);
}
```

- [ ] **Step 4: Create AttachmentRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Attachment;

public interface AttachmentRepository extends BaseCrudRepository<Attachment> {
  List<Attachment> findByCompletedTaskId(Long completedTaskId);
}
```

- [ ] **Step 5: Create NotificationRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Notification;

public interface NotificationRepository {
  Notification create(Notification notification);
  List<Notification> findByUserId(Long userId);
  void markAsRead(Long notificationId);
  void markAllAsReadByUserId(Long userId);
}
```

- [ ] **Step 6: Create PaymentRepository**

```java
package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Payment;

public interface PaymentRepository extends BaseCrudRepository<Payment> {
  List<Payment> findByStudentId(Long studentId);
}
```

- [ ] **Step 7: Commit**

```bash
git add src/main/java/org/example/crmedu/domain/repository/
git commit -m "feat: add domain repository interfaces for Homework, CompletedTask, CheckedTask, Attachment, Notification, Payment"
```

---

## Task 7: Infrastructure repository implementations

**Files:**
- Create: all Data*Repository and *RepositoryImpl files listed in File Structure above

- [ ] **Step 1: Create DataHomeworkRepository + HomeworkRepositoryImpl**

DataHomeworkRepository:
```java
package org.example.crmedu.infrastructure.repository.homework;

import java.util.List;
import org.example.crmedu.domain.enums.HomeworkStatus;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataHomeworkRepository extends BaseDataRepository<HomeworkEntity, Long> {
  List<HomeworkEntity> findAllByLessonEntity_Id(Long lessonId);
  List<HomeworkEntity> findAllByStatusAndLessonEntity_IdIn(HomeworkStatus status, List<Long> lessonIds);
}
```

HomeworkRepositoryImpl:
```java
package org.example.crmedu.infrastructure.repository.homework;

import java.util.List;
import org.example.crmedu.domain.enums.HomeworkStatus;
import org.example.crmedu.domain.model.Homework;
import org.example.crmedu.domain.repository.HomeworkRepository;
import org.example.crmedu.infrastructure.entity.HomeworkEntity;
import org.example.crmedu.infrastructure.mapping.HomeworkEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class HomeworkRepositoryImpl extends BaseRepository<Homework, HomeworkEntity, Long> implements HomeworkRepository {

  private final DataHomeworkRepository homeworkRepository;
  private final HomeworkEntityMapper mapper;

  public HomeworkRepositoryImpl(DataHomeworkRepository homeworkRepository, HomeworkEntityMapper mapper) {
    super(homeworkRepository, mapper);
    this.homeworkRepository = homeworkRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Homework> findByLessonId(Long lessonId) {
    return mapper.toDomain(homeworkRepository.findAllByLessonEntity_Id(lessonId));
  }

  @Override
  public List<Homework> findPublishedByLessonIds(List<Long> lessonIds) {
    return mapper.toDomain(homeworkRepository.findAllByStatusAndLessonEntity_IdIn(HomeworkStatus.PUBLISHED, lessonIds));
  }
}
```

- [ ] **Step 2: Create DataCompletedTaskRepository + CompletedTaskRepositoryImpl**

Similar pattern. DataCompletedTaskRepository uses Spring Data query derivation:
- `findAllByTaskEntity_HomeworkEntity_Id(Long homeworkId)`
- `findAllByStudentEntity_Id(Long studentId)`
- `findByTaskEntity_IdAndStudentEntity_Id(Long taskId, Long studentId)`

- [ ] **Step 3: Create DataCheckedTaskRepository + CheckedTaskRepositoryImpl**

- `findByCompletedTaskEntity_Id(Long completedTaskId)`
- `findAllByTutorEntity_Id(Long tutorId)`
- `findAllByTutorEntity_IdAndIsCheckedFalse(Long tutorId)`

- [ ] **Step 4: Create DataAttachmentRepository + AttachmentRepositoryImpl**

- `findAllByCompletedTaskEntity_Id(Long completedTaskId)`

- [ ] **Step 5: Create DataNotificationRepository + NotificationRepositoryImpl**

NotificationRepository does NOT extend BaseCrudRepository, so NotificationRepositoryImpl does NOT extend BaseRepository. It wraps DataNotificationRepository directly:

```java
@Component
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
  private final DataNotificationRepository notificationRepository;
  private final NotificationEntityMapper mapper;

  @Override
  public Notification create(Notification notification) {
    var entity = mapper.toEntity(notification);
    return mapper.toDomain(notificationRepository.save(entity));
  }

  @Override
  public List<Notification> findByUserId(Long userId) {
    return notificationRepository.findAllByUser_IdOrderByCreatedAtDesc(userId)
        .stream().map(mapper::toDomain).toList();
  }

  @Override
  public void markAsRead(Long notificationId) {
    notificationRepository.findById(notificationId).ifPresent(n -> {
      n.setIsRead(true);
      notificationRepository.save(n);
    });
  }

  @Override
  public void markAllAsReadByUserId(Long userId) {
    var notifications = notificationRepository.findAllByUser_IdAndIsReadFalse(userId);
    notifications.forEach(n -> n.setIsRead(true));
    notificationRepository.saveAll(notifications);
  }
}
```

- [ ] **Step 6: Create DataPaymentRepository + PaymentRepositoryImpl**

- `findAllByStudentEntity_IdOrderByPaymentDateDesc(Long studentId)`

- [ ] **Step 7: Commit**

```bash
git add src/main/java/org/example/crmedu/infrastructure/repository/
git commit -m "feat: add repository implementations for Homework, CompletedTask, CheckedTask, Attachment, Notification, Payment"
```

---

## Task 8: Domain services — Homework

**Files:**
- Create: `src/main/java/org/example/crmedu/domain/service/homework/HomeworkService.java`
- Create: `src/main/java/org/example/crmedu/domain/service/homework/HomeworkServiceImpl.java`

- [ ] **Step 1: Create HomeworkService interface**

```java
package org.example.crmedu.domain.service.homework;

import java.util.List;
import org.example.crmedu.domain.model.*;

public interface HomeworkService {
  Homework createDraft(Homework homework);
  Homework findById(Long id);
  void updateDraft(Homework homework, Long id);
  void deleteDraft(Long id);
  Homework publish(Long id);
  List<Homework> getHomeworkForStudent(Long userId);
  List<CompletedTask> getSubmissionsForReview(Long tutorUserId);
  CompletedTask submitAnswer(Long homeworkId, Long userId, String answer, List<String> attachmentUrls);
  CheckedTask gradeSubmission(Long completedTaskId, Long tutorUserId, CompletedTaskStatus status, String comments);
}
```

- [ ] **Step 2: Create HomeworkServiceImpl**

Key logic:
- `createDraft`: creates homework with status=DRAFT
- `updateDraft`: only allows updating if status=DRAFT
- `deleteDraft`: only allows deleting if status=DRAFT
- `publish`: sets status=PUBLISHED
- `getHomeworkForStudent`: gets student by userId, gets active subscriptions, gets lesson IDs, finds published homework for those lessons
- `getSubmissionsForReview`: gets tutor by userId, finds unchecked tasks for that tutor's lessons
- `submitAnswer`: creates CompletedTask linked to student and homework's first task, creates attachments
- `gradeSubmission`: creates or updates CheckedTask

- [ ] **Step 3: Commit**

```bash
git add src/main/java/org/example/crmedu/domain/service/homework/
git commit -m "feat: add HomeworkService with draft/publish/submit/grade logic"
```

---

## Task 9: Domain services — Payment and Notification

**Files:**
- Create: `src/main/java/org/example/crmedu/domain/service/payment/PaymentService.java`
- Create: `src/main/java/org/example/crmedu/domain/service/payment/PaymentServiceImpl.java`
- Modify: `src/main/java/org/example/crmedu/domain/service/notification/NotificationService.java`
- Modify: `src/main/java/org/example/crmedu/domain/service/notification/NotificationServiceImpl.java`

- [ ] **Step 1: Create PaymentService interface**

```java
package org.example.crmedu.domain.service.payment;

import java.util.List;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.domain.model.Student;

public interface PaymentService {
  Student getBalance(Long userId);
  boolean hasDebt(Long userId);
  List<Payment> getPaymentHistory(Long userId);
}
```

- [ ] **Step 2: Create PaymentServiceImpl**

```java
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;
  private final StudentRepository studentRepository;

  @Override
  @Transactional
  public Student getBalance(Long userId) {
    return studentRepository.getStudentByUserId(userId);
  }

  @Override
  @Transactional
  public boolean hasDebt(Long userId) {
    var student = studentRepository.getStudentByUserId(userId);
    return student.getBalance() < 0;
  }

  @Override
  @Transactional
  public List<Payment> getPaymentHistory(Long userId) {
    var student = studentRepository.getStudentByUserId(userId);
    return paymentRepository.findByStudentId(student.getId());
  }
}
```

- [ ] **Step 3: Update NotificationService interface**

Add methods:
```java
Notification create(Notification notification);
void markAsRead(Long notificationId);
void markAllAsRead(Long userId);
```

- [ ] **Step 4: Rewrite NotificationServiceImpl with real implementation**

Replace stub with repository-backed implementation:
```java
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository notificationRepository;

  @Override
  public List<Notification> getUserNotifications(User user) {
    return notificationRepository.findByUserId(user.getId());
  }

  @Override
  public Notification create(Notification notification) {
    return notificationRepository.create(notification);
  }

  @Override
  public void markAsRead(Long notificationId) {
    notificationRepository.markAsRead(notificationId);
  }

  @Override
  public void markAllAsRead(Long userId) {
    notificationRepository.markAllAsReadByUserId(userId);
  }
}
```

- [ ] **Step 5: Commit**

```bash
git add src/main/java/org/example/crmedu/domain/service/
git commit -m "feat: add PaymentService, rewrite NotificationService with real repository implementation"
```

---

## Task 10: Application DTOs

**Files:**
- Create: all DTO files listed in File Structure

- [ ] **Step 1: Create homework request DTOs**

CreateHomeworkRequest: lessonId (Long, @NotNull), title (String, @NotBlank), description (String, @NotBlank), rightAnswer (String), startDate (ZonedDateTime), endDate (ZonedDateTime)

UpdateHomeworkRequest: same fields as create (minus lessonId)

SubmitAnswerRequest: answer (String, @NotBlank), attachmentUrls (List<String>)

GradeHomeworkRequest: status (CompletedTaskStatus, @NotNull), comments (String)

- [ ] **Step 2: Create homework response DTOs**

GetHomeworkResponse: id, lessonId, title, description, startDate, endDate, status

GetCompletedTaskResponse: id, homeworkId, studentId, studentName, studentAnswer, status, dueDate, attachmentUrls (List<String>)

GetCheckedTaskResponse: id, completedTaskId, tutorId, tutorName, isChecked, comments, inspectionDate

- [ ] **Step 3: Create payment DTOs**

GetBalanceResponse: studentId, balance (Integer), hasDebt (Boolean)

GetPaymentResponse: id, amount, description, paymentType, paymentDate, status

- [ ] **Step 4: Create notification DTO**

GetNotificationResponse: id, title, description, link, notificationType, createdAt, isRead

- [ ] **Step 5: Commit**

```bash
git add src/main/java/org/example/crmedu/application/dto/
git commit -m "feat: add DTOs for homework, payment, and notification endpoints"
```

---

## Task 11: Application DTO mappers

**Files:**
- Create: `src/main/java/org/example/crmedu/application/mapping/HomeworkDTOMapper.java`
- Create: `src/main/java/org/example/crmedu/application/mapping/PaymentDTOMapper.java`
- Create: `src/main/java/org/example/crmedu/application/mapping/NotificationDTOMapper.java`

- [ ] **Step 1: Create HomeworkDTOMapper**

```java
@Mapper(componentModel = "spring")
public interface HomeworkDTOMapper {
  @Mapping(target = "lesson.id", source = "lessonId")
  Homework createRequestToHomework(CreateHomeworkRequest request);

  Homework updateRequestToHomework(UpdateHomeworkRequest request);

  @Mapping(target = "lessonId", source = "lesson.id")
  GetHomeworkResponse homeworkToGetResponse(Homework homework);

  List<GetHomeworkResponse> homeworksToGetResponses(List<Homework> homeworks);

  @Mapping(target = "homeworkId", source = "task.lesson.id")
  @Mapping(target = "studentId", source = "student.id")
  @Mapping(target = "studentName", expression = "java(ct.getStudent().getUser().getName())")
  GetCompletedTaskResponse completedTaskToGetResponse(CompletedTask ct);

  List<GetCompletedTaskResponse> completedTasksToGetResponses(List<CompletedTask> tasks);

  GetCheckedTaskResponse checkedTaskToGetResponse(CheckedTask ct);
}
```

- [ ] **Step 2: Create PaymentDTOMapper**

```java
@Mapper(componentModel = "spring")
public interface PaymentDTOMapper {
  GetPaymentResponse paymentToGetResponse(Payment payment);
  List<GetPaymentResponse> paymentsToGetResponses(List<Payment> payments);
}
```

- [ ] **Step 3: Create NotificationDTOMapper**

```java
@Mapper(componentModel = "spring")
public interface NotificationDTOMapper {
  GetNotificationResponse notificationToGetResponse(Notification notification);
  List<GetNotificationResponse> notificationsToGetResponses(List<Notification> notifications);
}
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/org/example/crmedu/application/mapping/
git commit -m "feat: add DTO mappers for Homework, Payment, Notification"
```

---

## Task 12: HomeworkController

**Files:**
- Create: `src/main/java/org/example/crmedu/application/controller/HomeworkController.java`

- [ ] **Step 1: Create HomeworkController**

Endpoints:
```
POST   api/v1/homework           — create draft (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
GET    api/v1/homework/{id}      — get by id (authenticated)
PUT    api/v1/homework/{id}      — update draft (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
DELETE api/v1/homework/{id}      — delete draft (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
PATCH  api/v1/homework/{id}/publish — publish (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
GET    api/v1/homework/student   — list for current student (authenticated)
GET    api/v1/homework/review    — list submissions for review (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
POST   api/v1/homework/{id}/submit — submit answer (@Secured STUDENT)
POST   api/v1/homework/submissions/{completedTaskId}/grade — grade (@Secured TUTOR, ORG_ADMIN, SUPERUSER)
```

- [ ] **Step 2: Commit**

```bash
git add src/main/java/org/example/crmedu/application/controller/HomeworkController.java
git commit -m "feat: add HomeworkController with full homework lifecycle endpoints"
```

---

## Task 13: PaymentController

**Files:**
- Create: `src/main/java/org/example/crmedu/application/controller/PaymentController.java`

- [ ] **Step 1: Create PaymentController**

Endpoints:
```
GET api/v1/payments/balance  — get current balance (authenticated)
GET api/v1/payments/history  — get payment history (authenticated)
```

- [ ] **Step 2: Commit**

```bash
git add src/main/java/org/example/crmedu/application/controller/PaymentController.java
git commit -m "feat: add PaymentController with balance and history endpoints"
```

---

## Task 14: Fix UserController notifications endpoint

**Files:**
- Modify: `src/main/java/org/example/crmedu/application/controller/UserController.java`

- [ ] **Step 1: Replace raw Notification return with DTO**

Change `GET /notifications` endpoint:
- Inject `NotificationDTOMapper` and `NotificationService`
- Return `List<GetNotificationResponse>` instead of `List<Notification>`
- Add `PATCH /notifications/{id}/read` endpoint for marking single as read
- Add `PATCH /notifications/read-all` endpoint for marking all as read

- [ ] **Step 2: Commit**

```bash
git add src/main/java/org/example/crmedu/application/controller/UserController.java
git commit -m "feat: update UserController notifications to use DTO, add mark-as-read endpoints"
```

---

## Task 15: Notification generation on homework events

**Files:**
- Modify: `src/main/java/org/example/crmedu/domain/service/homework/HomeworkServiceImpl.java`

- [ ] **Step 1: Add notification creation calls in HomeworkServiceImpl**

On `publish`: create HOMEWORK notification for all students subscribed to the lesson's program
On `gradeSubmission`: create HOMEWORK notification for the student whose work was graded

- [ ] **Step 2: Commit**

```bash
git add src/main/java/org/example/crmedu/domain/service/homework/
git commit -m "feat: add notification generation on homework publish and grade events"
```

---

## Task 16: Verify build compiles

- [ ] **Step 1: Run build**

```bash
./gradlew compileJava
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: Fix any compilation errors and commit**

---

## Task 17: Basic tests for homework endpoints

**Files:**
- Create test files for HomeworkService and HomeworkController

- [ ] **Step 1: Write unit test for HomeworkServiceImpl — create draft, publish, delete draft**
- [ ] **Step 2: Run tests, ensure they pass**
- [ ] **Step 3: Write integration test for HomeworkController — basic endpoint smoke tests**
- [ ] **Step 4: Run tests, ensure they pass**
- [ ] **Step 5: Commit**

```bash
git add src/test/
git commit -m "test: add tests for homework service and controller"
```

---

## Endpoint Summary

| Method | Path | Description | Access |
|--------|------|-------------|--------|
| POST | /api/v1/homework | Create homework draft | TUTOR, ORG_ADMIN, SUPERUSER |
| GET | /api/v1/homework/{id} | Get homework by ID | Authenticated |
| PUT | /api/v1/homework/{id} | Update draft | TUTOR, ORG_ADMIN, SUPERUSER |
| DELETE | /api/v1/homework/{id} | Delete draft | TUTOR, ORG_ADMIN, SUPERUSER |
| PATCH | /api/v1/homework/{id}/publish | Publish homework | TUTOR, ORG_ADMIN, SUPERUSER |
| GET | /api/v1/homework/student | List homework for student | Authenticated |
| GET | /api/v1/homework/review | List submissions for review | TUTOR, ORG_ADMIN, SUPERUSER |
| POST | /api/v1/homework/{id}/submit | Submit answer | STUDENT |
| POST | /api/v1/homework/submissions/{id}/grade | Grade submission | TUTOR, ORG_ADMIN, SUPERUSER |
| GET | /api/v1/payments/balance | Get balance + debt flag | Authenticated |
| GET | /api/v1/payments/history | Get payment history | Authenticated |
| GET | /api/v1/users/notifications | Get notifications (DTO) | Authenticated |
| PATCH | /api/v1/users/notifications/{id}/read | Mark notification read | Authenticated |
| PATCH | /api/v1/users/notifications/read-all | Mark all read | Authenticated |