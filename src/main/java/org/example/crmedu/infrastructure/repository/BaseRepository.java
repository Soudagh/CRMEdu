package org.example.crmedu.infrastructure.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.infrastructure.mapping.BaseEntityMapper;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public abstract class BaseRepository<D, E, ID> {

  private final BaseDataRepository<E, ID> baseRepository;

  private final BaseEntityMapper<D, E> baseMapper;

  public D create(D model) {
    var requestedEntity = baseMapper.toEntity(model);
    var responsedEntity = baseRepository.save(requestedEntity);
    return baseMapper.toDomain(responsedEntity);
  }

  public Optional<D> findById(ID id) {
    return baseRepository.findById(id).map(baseMapper::toDomain);
  }

  public Page<D> findAll(int pageNumber, int pageSize) {
    var page = baseRepository.findAll(
        PageRequest.of(
            pageNumber,
            pageSize
        )
    ).map(baseMapper::toDomain);
    return new Page<D>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  public void update(D model) {
    baseRepository.save(baseMapper.toEntity(model));
  }

  public void delete(ID id) {
    baseRepository.deleteById(id);
  }

}
