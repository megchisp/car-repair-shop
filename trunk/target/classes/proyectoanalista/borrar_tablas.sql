
---------------------------- ELIMINACION DE TABLAS ----------------------------------
DROP TABLE cliente cascade;
DROP TABLE automovil cascade;
DROP TABLE repuesto cascade;
DROP TABLE mano_de_obra cascade;
DROP TABLE mecanico cascade;
DROP TABLE proveedor cascade;
DROP TABLE servicio cascade;
DROP TABLE tipo_de_servicio cascade;
DROP TABLE reparacion cascade;

---------------------------- ELIMINACION DE SECUENCIAS ----------------------------------

DROP SEQUENCE cliente_id_cliente_seq;
DROP SEQUENCE automovil_id_automovil_seq;
DROP SEQUENCE reparacion_id_reparacion_seq;
DROP SEQUENCE mecanico_id_mecanico_seq;
DROP SEQUENCE proveedor_id_proveedor_seq;
DROP SEQUENCE tipo_de_servicio_id_tipo_de_servicio_seq;
DROP SEQUENCE servicio_id_servicio_seq;
DROP SEQUENCE repuesto_id_repuesto_seq;
DROP SEQUENCE mano_de_obra_id_mano_de_obra_seq;
