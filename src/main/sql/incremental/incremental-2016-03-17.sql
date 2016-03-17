alter table BMATDigitalChartsDB.Right
add column code varchar(255);

update BMATDigitalChartsDB.Right
set code = "SDF" where name = "Ad Streams";

--update BMATDigitalChartsDB.Right
--set code = "" where name = "Capped Streams";

update BMATDigitalChartsDB.Right
set code = "SDP" where name = "Premium Streams";

update BMATDigitalChartsDB.Right
set code = "TDS" where name = "Total Streams";

--update BMATDigitalChartsDB.Right
--set code = "" where name = "Unique Listeners";

update BMATDigitalChartsDB.Right
set code = "DDR" where name = "Radio Streams";

update BMATDigitalChartsDB.Right
set code = "DDT" where name = "Downloads";


