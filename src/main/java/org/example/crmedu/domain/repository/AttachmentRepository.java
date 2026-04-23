package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Attachment;

public interface AttachmentRepository extends BaseCrudRepository<Attachment> {

  List<Attachment> findByCompletedTaskId(Long completedTaskId);
}