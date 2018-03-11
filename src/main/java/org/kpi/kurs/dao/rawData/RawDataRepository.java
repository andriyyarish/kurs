package org.kpi.kurs.dao.rawData;

import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RawDataRepository extends CrudRepository<RawDataEntity, Long> {
    List<RawDataEntity> findBySourceId(SourcesEnum source);
    List<RawDataEntity> findByBaseDateBetween(Date from, Date to);
    List<RawDataEntity> findByBaseDateBetweenAndSourceId(Date from, Date to, SourcesEnum source);

}
