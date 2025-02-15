package org.example.crmedu.domain.model;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a tutor entity. This class is used as a domain model.
 */
@Data
@Accessors(chain = true)
public class Tutor {

  /**
   * The unique identifier of the tutor.
   */
  private Long id;

  /**
   * The tutor's account in system.
   */
  private User user;

  /**
   * The list of tutors social medias for communicate.
   */
  private List<Link> socials;

  /**
   * Any notes about tutor.
   */
  private String notes;

  /**
   * Subjects that a tutor can teach.
   */
  private Set<Subject> subjects;

  /**
   * Classes of study that a tutor can teach.
   */
  private Set<Integer> grades;

  private Set<TutorSchedule> schedule;
}
