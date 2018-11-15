create database if not exists agendatelefonica;
use agendatelefonica;

set foreign_key_checks = 0;

create table contato (
	id int not null auto_increment primary key,
    nome varchar(80) not null,
    datanascimento date not null,
    email varchar(70) not null,
    id_tipocontato int not null,
    constraint fk_contatotipo foreign key (id_tipocontato) references tipocontato (id)
);

create table tipocontato (
	id int not null auto_increment primary key,
    nome varchar(50) not null
);

create table telefone (
	id int not null auto_increment primary key,
    ddd int not null,
    telefone varchar(20) not null,
    id_contato int not null,
    constraint fk_telcontato foreign key (id_contato) references contato (id)
);

set foreign_key_checks = 1;

insert into tipocontato (nome) values ('Celular'), ('Residencial'), ('Comercial'), ('Serviço');