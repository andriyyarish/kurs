package org.kpi.kurs.dao.preAnalyzedData;

import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface DataAnalyzeRepository extends CrudRepository<TempDiffsEntity, Long> {
    List<TempDiffsEntity> findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(SourcesEnum source, Date date);
}
