Delimiter //
Create Procedure calc_aggregated_ratio()
Begin
	SET @totalSum = (select sum(reliability_ratio) from sources_historical_reliability);
	Update sources_aggregated_reliability aggr
	join (
			select source, (sum(reliability_ratio)/@totalSum) as ratio from sources_historical_reliability group by source) as tmp 
		on aggr.source=tmp.source
    Set aggr.ratio = tmp.ratio;
End 
Delimiter ;


	