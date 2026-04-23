package org.example.crmedu.domain.service.attachment;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.OperationNotAllowedException;
import org.example.crmedu.domain.model.Attachment;
import org.example.crmedu.domain.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

  private final AttachmentRepository attachmentRepository;

  @Override
  @Transactional
  public Attachment upload(MultipartFile file) {
    if (file.isEmpty()) {
      throw new OperationNotAllowedException("Uploaded file must not be empty");
    }
    try {
      var attachment = new Attachment()
          .setContent(file.getBytes())
          .setMimeType(file.getContentType())
          .setOriginalName(file.getOriginalFilename())
          .setUrl("placeholder");
      var saved = attachmentRepository.create(attachment);
      saved.setUrl("/api/v1/attachments/" + saved.getId());
      attachmentRepository.update(saved);
      return saved;
    } catch (IOException e) {
      throw new RuntimeException("Failed to read uploaded file", e);
    }
  }

  @Override
  public Attachment findById(Long id) {
    return attachmentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Attachment.class, id));
  }
}
