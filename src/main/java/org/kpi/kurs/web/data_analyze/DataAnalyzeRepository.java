package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface DataAnalyzeRepository extends CrudRepository<TempDiffsDto, Long> {
    List<TempDiffsDto> findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(SourcesEnum source, Date date);
}
