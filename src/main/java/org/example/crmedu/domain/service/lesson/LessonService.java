package org.example.crmedu.domain.service.lesson;

import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.LessonQr;
import org.example.crmedu.domain.service.BaseCrudService;

public interface LessonService extends BaseCrudService<Lesson> {

  void patchLessonLink(String link, Long id);

  void patchLessonNotes(String notes, Long id);

  LessonQr generateQr(Long id);
}
