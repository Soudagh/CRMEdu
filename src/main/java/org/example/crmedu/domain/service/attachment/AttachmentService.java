package org.example.crmedu.domain.service.attachment;

import org.example.crmedu.domain.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

  Attachment upload(MultipartFile file);

  Attachment findById(Long id);
}
