CREATE TABLE usuario (
	id UUID primary key,
	email text unique,
	senha text,
	telefone varchar(15),
	isAdmin boolean 
);


CREATE TABLE categoria(
	id UUID primary key,
	nome varchar(30) unique,
	descricao text
);

CREATE TABLE organizador(
	id UUID primary key,
	nome varchar(50) unique,
	telefone varchar(15),
	email text unique
);

CREATE TABLE endereco(
	id UUID primary key,
	cidade text,
	CEP char(9),
	rua text,
	numero integer,
	complemento text
);

CREATE TABLE horario(
	id UUID PRIMARY KEY,
    data date,
    hora time
);

CREATE TABLE registro(
	id UUID PRIMARY KEY,
	imagem_path text,
	descricao text
);

CREATE TABLE acao(
	id UUID PRIMARY KEY,
	titulo text,
	subTitulo text,
	descricao text,
	categoria_id UUID,
	organizador_id UUID,
	endereco_id UUID,
	horario_id UUID,
	registro_id UUID,
	FOREIGN KEY (categoria_id) REFERENCES categoria(id),
	FOREIGN KEY (organizador_id) REFERENCES organizador(id),
	FOREIGN KEY (endereco_id) REFERENCES endereco(id),
	FOREIGN KEY (horario_id) REFERENCES horario(id),
	FOREIGN KEY (registro_id) REFERENCES registro(id)
);

