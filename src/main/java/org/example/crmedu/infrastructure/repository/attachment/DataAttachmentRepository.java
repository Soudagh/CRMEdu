package org.example.crmedu.infrastructure.repository.attachment;

import java.util.List;
import org.example.crmedu.infrastructure.entity.AttachmentEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataAttachmentRepository extends BaseDataRepository<AttachmentEntity, Long> {

  List<AttachmentEntity> findAllByCompletedTaskEntity_Id(Long completedTaskId);
}