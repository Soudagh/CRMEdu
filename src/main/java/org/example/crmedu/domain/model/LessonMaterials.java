package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LessonMaterials {

  private Long id;

  private Lesson lesson;

  private String name;

  private String url;
}
