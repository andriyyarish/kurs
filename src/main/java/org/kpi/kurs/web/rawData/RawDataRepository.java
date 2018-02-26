package org.kpi.kurs.web.rawData;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RawDataRepository extends CrudRepository<RawDataEntity, Long> {
    List<RawDataEntity> findBySourceName(SourcesEnum source);
    List<RawDataEntity> findByBaseDateBetween(Date from, Date to);
    List<RawDataEntity> findByBaseDateBetweenAndSourceName(Date from, Date to, SourcesEnum source);

}
