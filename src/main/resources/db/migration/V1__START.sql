/*
 * Iniciando o banco de dados
 */
 
CREATE TABLE TUSERS (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	USERNAME VARCHAR(255) ,
	PASSWORD VARCHAR(255) ,
	CONSTRAINT TUSERS_PKEY PRIMARY KEY (ID)
);