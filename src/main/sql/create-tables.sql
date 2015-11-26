create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombreApellido varchar(255),
	username varchar(255),
	password varchar(255),
	email varchar(255),
	fechaBaja datetime,
	fechaEliminacion datetime
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(username);

create table Rol(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_rol_nombre ON Rol(nombre);

create table Usuario_Rol(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	usuario_id BIGINT NOT NULL,
	rol_id BIGINT NOT NULL,
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (rol_id) REFERENCES Rol(id)
)  ENGINE=InnoDB;

