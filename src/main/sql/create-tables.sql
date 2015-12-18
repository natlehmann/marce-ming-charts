create table User(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username varchar(255),
	password varchar(255)
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_user_username ON User(username);

create table Role(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_role_name ON Role(name);

create table User_Role(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES User(id),
	FOREIGN KEY (role_id) REFERENCES Role(id)
)  ENGINE=InnoDB;

create table SummaryReport(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	dateFrom datetime not null,
	dateTo datetime not null,
	year int not null,
	weekFrom int not null,
	weekTo int not null,
	month int,
	previousDateFrom datetime,
	previousDateTo datetime,
	country_id BIGINT NOT NULL,
	right_id BIGINT NOT NULL,
	enabled bit not null default true,
	FOREIGN KEY (country_id) REFERENCES Country(id),
	FOREIGN KEY (right_id) REFERENCES BMATDigitalChartsDB.Right(id)
)  ENGINE=InnoDB;

create table SummaryReportItem(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	summaryReport_id BIGINT NOT NULL,
	currentPosition int not null,
	previousPosition int,
	positionBeforePrevious int,
	songId BIGINT NOT NULL,
	songName varchar(255),
	performerId BIGINT NOT NULL,
	performerName varchar(255),
	currentAmount BIGINT NOT NULL,
	previousAmount BIGINT,
	labelCompanyId BIGINT,
	labelCompanyName varchar(255),
	FOREIGN KEY (summaryReport_id) REFERENCES SummaryReport(id)
)  ENGINE=InnoDB;

