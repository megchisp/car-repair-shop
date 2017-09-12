---------------------------- CREACION DE SECUENCIAS ----------------------------------

-- Secuencia cliente
CREATE SEQUENCE cliente_id_cliente_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE cliente_id_cliente_seq
  OWNER TO postgres;

-- Secuencia automovil
CREATE SEQUENCE automovil_id_automovil_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE automovil_id_automovil_seq
  OWNER TO postgres;

  -- Secuencia reparacion
CREATE SEQUENCE reparacion_id_reparacion_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE reparacion_id_reparacion_seq
  OWNER TO postgres;

--  Secuencia mecanico
CREATE SEQUENCE mecanico_id_mecanico_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE mecanico_id_mecanico_seq
  OWNER TO postgres;

 -- Secuencia proveedor
 CREATE SEQUENCE proveedor_id_proveedor_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE proveedor_id_proveedor_seq
  OWNER TO postgres;
  
  -- Secuencia tipo_de_servicio
  CREATE SEQUENCE tipo_de_servicio_id_tipo_de_servicio_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE tipo_de_servicio_id_tipo_de_servicio_seq
  OWNER TO postgres;

 -- Secuencia servicio
 CREATE SEQUENCE servicio_id_servicio_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE servicio_id_servicio_seq
  OWNER TO postgres;

-- Secuencia repuesto
CREATE SEQUENCE repuesto_id_repuesto_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE repuesto_id_repuesto_seq
  OWNER TO postgres;

-- Secuencia mano_de_obra
CREATE SEQUENCE mano_de_obra_id_mano_de_obra_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE mano_de_obra_id_mano_de_obra_seq
  OWNER TO postgres;
  
  -- Secuencia usuario
CREATE SEQUENCE usuario_id_usuario_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE usuario_id_usuario_seq
  OWNER TO postgres;


---------------------------- CREACION DE TABLAS ----------------------------------

-- Tabla cliente 
CREATE TABLE cliente
(
  id_cliente int NOT NULL,
  cuit text,
  fecha_nacimiento date,
  fecha_nacimiento_habilitada boolean,
  fallecido boolean,
  nombre text,
  apellido text,
  telefono text,
  celular text,
  domicilio text,
  localidad text,
  codigo_postal text,
  mail text,
  observaciones text,
  PRIMARY KEY (id_cliente)
);

-- Tabla automovil
CREATE TABLE automovil
(
  id_automovil int NOT NULL,
  id_cliente integer NOT NULL,
  dominio text,
  num_motor text,
  num_chasis text,
  marca text,
  modelo text,
  anio integer,
  color integer,
  aceite text,
  tipo_combustible text,
  con_gnc boolean,
  num_radio text,
  codigo_llave text,
  uso text,
  PRIMARY KEY (id_automovil),
  FOREIGN KEY (id_cliente)
      REFERENCES cliente (id_cliente)
      ON UPDATE RESTRICT ON DELETE CASCADE
);

-- Tabla reparacion
 CREATE TABLE reparacion
(
  id_reparacion int NOT NULL,
  id_automovil integer NOT NULL,
  fecha date,
  km integer,
  observaciones text,
  PRIMARY KEY (id_reparacion),
  FOREIGN KEY (id_automovil)
      REFERENCES automovil (id_automovil)
      ON UPDATE RESTRICT ON DELETE CASCADE
);

-- Tabla mecanico 
CREATE TABLE mecanico
(
  id_mecanico int NOT NULL,
  nombre text,
  apellido text,
  telefono_fijo text,
  telefono_celular text,
  domicilio text,
  localidad text,
  codigo_postal text,
  PRIMARY KEY (id_mecanico)
);

-- Tabla proveedor
CREATE TABLE proveedor
(
  id_proveedor int NOT NULL,
  nombre text,
  telefono text,
  telefono_alternativo text,
  PRIMARY KEY (id_proveedor)
);

-- Tabla tipo de servicio
 CREATE TABLE tipo_de_servicio
(
  id_tipo_de_servicio int NOT NULL,
  nombre text,
  tiempo_min_reparacion_estimado integer,
  tiempo_max_reparacion_estimado integer,
  PRIMARY KEY (id_tipo_de_servicio)
);

-- Tabla servicio
 CREATE TABLE servicio
 (
	id_servicio int NOT NULL,
	id_reparacion integer NOT NULL,
	id_tipo_de_servicio integer NOT NULL,
	PRIMARY KEY (id_servicio),
	FOREIGN KEY (id_reparacion)
      REFERENCES reparacion (id_reparacion)
      ON UPDATE RESTRICT ON DELETE CASCADE,
	FOREIGN KEY (id_tipo_de_servicio)
      REFERENCES tipo_de_servicio (id_tipo_de_servicio)
      ON UPDATE RESTRICT ON DELETE CASCADE
);

-- Tabla repuesto
CREATE TABLE repuesto
(
  id_repuesto int NOT NULL,
  id_proveedor integer,
  id_servicio integer NOT NULL,
  nombre text,
  precio_unitario float,
  cantidad integer,
  observaciones text,
  PRIMARY KEY (id_repuesto),
  FOREIGN KEY (id_proveedor)
      REFERENCES proveedor (id_proveedor)
      ON UPDATE RESTRICT ON DELETE SET NULL,
  FOREIGN KEY (id_servicio)
      REFERENCES servicio (id_servicio)
      ON UPDATE RESTRICT ON DELETE CASCADE
);

-- Tabla mano_de_obra
CREATE TABLE mano_de_obra
(
  id_mano_de_obra int NOT NULL,
  id_servicio integer NOT NULL,
  id_mecanico integer,
  nombre text,
  precio_mano_de_obra float,
  observaciones text,
  PRIMARY KEY (id_mano_de_obra),
  FOREIGN KEY (id_servicio)
      REFERENCES servicio (id_servicio)
      ON UPDATE RESTRICT ON DELETE CASCADE,
  FOREIGN KEY (id_mecanico)
	  REFERENCES mecanico (id_mecanico)
	  ON UPDATE RESTRICT ON DELETE SET NULL
);

-- Tabla marca automoviles
CREATE TABLE marca_automoviles
(
  id_marca_automoviles int NOT NULL,
  nombre text,
  PRIMARY KEY (id_marca_automoviles)
);

-- Tabla modelo automoviles
CREATE TABLE modelo_automoviles
(
  id_modelo_automoviles int NOT NULL,
  id_marca_automoviles integer NOT NULL,
  nombre text,
  PRIMARY KEY (id_modelo_automoviles),
  FOREIGN KEY (id_marca_automoviles)
      REFERENCES marca_automoviles (id_marca_automoviles)
      ON UPDATE RESTRICT ON DELETE CASCADE
);

-- Tabla usuario
CREATE TABLE usuario
(
  id_usuario int NOT NULL,
  username text,
  password text,
  privilegio int,
  last_login date,
  PRIMARY KEY (id_usuario)
);

---------------------------- ASIGNACION DE PROPIETARIO A TABLAS ----------------------------------

ALTER TABLE cliente
  OWNER TO postgres;
  
ALTER TABLE automovil
  OWNER TO postgres;
  
ALTER TABLE reparacion
  OWNER TO postgres;  

ALTER TABLE mecanico
  OWNER TO postgres;
 
ALTER TABLE proveedor
  OWNER TO postgres;
  
ALTER TABLE repuesto
  OWNER TO postgres;

ALTER TABLE tipo_de_servicio
  OWNER TO postgres;

ALTER TABLE servicio
  OWNER TO postgres;

ALTER TABLE mano_de_obra
  OWNER TO postgres;
  
ALTER TABLE usuario
  OWNER TO postgres;

 -- INICIALIZO TABLA USUARIO --
INSERT INTO USUARIO (id_usuario, username, password, privilegio, last_login) VALUES
('1', 'admin', 'c3f8dd55ed53236dce1f9154c36a6b3cd613cfa', '1', '1900-01-01');
 