package org.example.crmedu.infrastructure.repository.subject;

import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSubjectRepository extends JpaRepository<SubjectEntity, Long>, PagingAndSortingRepository<SubjectEntity, Long> {

  boolean existsByName(String name);
}
