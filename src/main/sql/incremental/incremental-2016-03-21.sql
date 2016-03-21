create table EmailAddress(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	email varchar(255) NOT NULL,
	restSourceId BIGINT NOT NULL,
	FOREIGN KEY (restSourceId) REFERENCES RestSource(id)
)  ENGINE=InnoDB;