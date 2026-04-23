package org.example.crmedu.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.response.attachment.UploadAttachmentResponse;
import org.example.crmedu.domain.service.attachment.AttachmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/attachments")
public class AttachmentController {

  private final AttachmentService attachmentService;

  @PostMapping("/upload")
  @Secured({"STUDENT", "TUTOR"})
  public ResponseEntity<UploadAttachmentResponse> upload(@RequestParam("file") MultipartFile file) {
    var saved = attachmentService.upload(file);
    return ResponseEntity.ok(
        new UploadAttachmentResponse()
            .setId(saved.getId())
            .setUrl(saved.getUrl())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> get(@PathVariable Long id) {
    var attachment = attachmentService.findById(id);
    String mime = attachment.getMimeType() != null ? attachment.getMimeType() : "application/octet-stream";
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, mime)
        .body(attachment.getContent());
  }
}
