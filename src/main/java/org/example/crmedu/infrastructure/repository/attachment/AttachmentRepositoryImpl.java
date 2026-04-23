package org.example.crmedu.infrastructure.repository.attachment;

import java.util.List;
import org.example.crmedu.domain.model.Attachment;
import org.example.crmedu.domain.repository.AttachmentRepository;
import org.example.crmedu.infrastructure.entity.AttachmentEntity;
import org.example.crmedu.infrastructure.mapping.AttachmentEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class AttachmentRepositoryImpl extends BaseRepository<Attachment, AttachmentEntity, Long>
    implements AttachmentRepository {

  private final DataAttachmentRepository attachmentRepository;
  private final AttachmentEntityMapper mapper;

  public AttachmentRepositoryImpl(DataAttachmentRepository attachmentRepository,
      AttachmentEntityMapper mapper) {
    super(attachmentRepository, mapper);
    this.attachmentRepository = attachmentRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Attachment> findByCompletedTaskId(Long completedTaskId) {
    return mapper.toDomain(attachmentRepository.findAllByCompletedTaskEntity_Id(completedTaskId));
  }
}