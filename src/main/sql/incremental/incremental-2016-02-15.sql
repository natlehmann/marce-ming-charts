ALTER TABLE WeeklyReportItem CHANGE labelCompanyId licensorId BIGINT;
ALTER TABLE WeeklyReportItem CHANGE labelCompanyName licensorName varchar(255);

ALTER TABLE MonthlyReportItem CHANGE labelCompanyId licensorId BIGINT;
ALTER TABLE MonthlyReportItem CHANGE labelCompanyName licensorName varchar(255);

alter table WeeklyReportItem
add column aggregateUnits DECIMAL(65,0) UNSIGNED;

alter table MonthlyReportItem
add column aggregateUnits DECIMAL(65,0) UNSIGNED;