package org.example.crmedu.domain.service.lesson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import org.example.crmedu.domain.components.QrSigner;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.LessonQr;
import org.example.crmedu.domain.model.QrPayload;
import org.example.crmedu.domain.repository.LessonRepository;
import org.example.crmedu.domain.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonServiceImpl extends BaseService<Lesson> implements LessonService  {

  private final LessonRepository lessonRepository;

  private final ObjectMapper objectMapper;

  private final QrSigner qrSigner;

  private static final Integer HOURS_BEFORE_EXPIRE = 2;

  public LessonServiceImpl(LessonRepository lessonRepository, ObjectMapper objectMapper, QrSigner qrSigner) {
    super(lessonRepository, Lesson.class);
    this.lessonRepository = lessonRepository;
    this.objectMapper = objectMapper;
    this.qrSigner = qrSigner;
  }

  @Override
  @Transactional
  public void patchLessonLink(String link, Long id) {
    var lesson = findById(id);
    lessonRepository.update(lesson.setLink(link));
  }

  @Override
  @Transactional
  public void patchLessonNotes(String notes, Long id) {
    var lesson = findById(id);
    lessonRepository.update(lesson.setNotes(notes));
  }

  @Override
  public LessonQr generateQr(Long id) {
    var expiredAt = ZonedDateTime.now().plusHours(HOURS_BEFORE_EXPIRE);
    var qrPayload = new QrPayload().setId(id).setExpiredAt(expiredAt);
    try {
      var unsignedJson = objectMapper.writeValueAsString(qrPayload);
      var signature = qrSigner.sign(unsignedJson);
      return new LessonQr().setExpiresAt(expiredAt).setQrPayload(signature);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Lesson create(Lesson lesson) {
    return null;
  }

  @Override
  public void update(Lesson lesson, Long id) {

  }
}
