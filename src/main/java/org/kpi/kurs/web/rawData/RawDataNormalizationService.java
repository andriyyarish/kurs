package org.kpi.kurs.web.rawData;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.dao.rawData.RawDataEntity;
import org.kpi.kurs.dao.rawData.RawDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class RawDataNormalizationService {
    public static final Logger logger = LogManager.getLogger(RawDataNormalizationService.class);
    @Autowired
    private RawDataRepository rawDataRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String getAllIdsRelatedToDuplicates = "select id from rawdata join (select source_id, base_date from db_kurs.rawdata  \n" +
            "\t\tgroup by source_id, base_date \n" +
            "\t\t\thaving count(base_date) > 1 \n" +
            "            order by base_date) as tmp on rawdata.base_date = tmp.base_date and rawdata.source_id=tmp.source_id;";
    private String getAvgRawDataDtos = "Select source_id, base_date,\n" +
            "avg(first_day_min_temp),  avg(first_day_max_temp), \n" +
            "avg(second_day_min_temp), avg(second_day_max_temp),\n" +
            "avg(third_day_min_temp), avg(third_day_max_temp), \n" +
            "avg(fourth_day_min_temp), avg(fourth_day_max_temp),\n" +
            "avg(fifth_day_min_temp), avg(fifth_day_max_temp), \n" +
            "avg(six_day_min_temp), avg(six_day_max_temp), \n" +
            "avg(seven_day_min_temp), avg(seven_day_max_temp)\n" +
            "\tfrom db_kurs.rawdata  \n" +
            "\t\tgroup by source_id, base_date \n" +
            "\t\t\thaving count(base_date) > 1 \n" +
            "            order by base_date;";

    public void replaceDuplicatesWithAvarage() {
        List<Integer> idsOfDuplicatedRaws = jdbcTemplate.query(getAllIdsRelatedToDuplicates, (resultSet, i) -> resultSet.getInt("id"));
        logger.trace(idsOfDuplicatedRaws);

        List<RawDataEntity> avgForDuplicates = jdbcTemplate.query(getAvgRawDataDtos, new RowMapper<RawDataEntity>() {
            @Override
            public RawDataEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                RawDataEntity rawDataEnt = new RawDataEntity();
                rawDataEnt.setSourceName(SourcesEnum.values()[resultSet.getInt(1)]);
                rawDataEnt.setBaseDate(resultSet.getDate(2));
                rawDataEnt.setFirstDayMinTemp(resultSet.getDouble(3));
                rawDataEnt.setFirstDayMaxTemp(resultSet.getDouble(4));
                rawDataEnt.setSecondDayMinTemp(resultSet.getDouble(5));
                rawDataEnt.setSecondDayMaxTemp(resultSet.getDouble(6));
                rawDataEnt.setThirdDayMinTemp(resultSet.getDouble(7));
                rawDataEnt.setThirdDayMaxTemp(resultSet.getDouble(8));
                rawDataEnt.setFourthDayMinTemp(resultSet.getDouble(9));
                rawDataEnt.setFourthDayMaxTemp(resultSet.getDouble(10));
                rawDataEnt.setFifthDayMinTemp(resultSet.getDouble(11));
                rawDataEnt.setFourthDayMaxTemp(resultSet.getDouble(12));
                rawDataEnt.setSixDayMinTemp(resultSet.getDouble(13));
                rawDataEnt.setSixDayMaxTemp(resultSet.getDouble(14));
                rawDataEnt.setSevenDayMinTemp(resultSet.getDouble(15));
                rawDataEnt.setSevenDayMaxTemp(resultSet.getDouble(16));
                return rawDataEnt;
            }
        });
        logger.trace(avgForDuplicates);
    }

    public void fillGaps() {

    }
}
