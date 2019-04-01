Select source_id, base_date, count(base_date),
avg(first_day_min_temp),  avg(first_day_max_temp), 
avg(second_day_min_temp), avg(second_day_max_temp),
avg(third_day_min_temp), avg(third_day_max_temp), 
avg(fourth_day_min_temp), avg(fourth_day_max_temp),
avg(fifth_day_min_temp), avg(fifth_day_max_temp), 
avg(six_day_min_temp), avg(six_day_max_temp), 
avg(seven_day_min_temp), avg(seven_day_max_temp)
	from db_kurs.rawdata  
		group by source_id, base_date 
			having count(base_date) > 1 
            order by base_date;
            
select id from rawdata join (select source_id, base_date from db_kurs.rawdata  
		group by source_id, base_date 
			having count(base_date) > 1 
            order by base_date) as tmp on rawdata.base_date = tmp.base_date and rawdata.source_id=tmp.source_id;


Insert into rawdata (source_id, base_date,
first_day_min_temp,  first_day_max_temp, 
second_day_min_temp, second_day_max_temp,
third_day_min_temp, third_day_max_temp, 
fourth_day_min_temp, fourth_day_max_temp,
fifth_day_min_temp, fifth_day_max_temp, 
six_day_min_temp, six_day_max_temp, 
seven_day_min_temp, seven_day_max_temp) duplicated_values;

