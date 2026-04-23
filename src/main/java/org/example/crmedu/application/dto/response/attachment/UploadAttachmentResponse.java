package org.example.crmedu.application.dto.response.attachment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UploadAttachmentResponse {

  private Long id;

  private String url;
}
