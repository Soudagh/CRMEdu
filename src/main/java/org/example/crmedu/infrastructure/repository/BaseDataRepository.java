package org.example.crmedu.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseDataRepository<E, ID> extends CrudRepository<E, ID>, PagingAndSortingRepository<E, ID> {

}
