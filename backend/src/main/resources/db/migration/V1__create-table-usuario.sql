create table usuario(
    id serial not null primary key,
    uuid UUID not null unique default gen_random_uuid(),
    nome varchar(60) not null,
    email varchar(120) not null UNIQUE,
    senha varchar(255) not null,
    telefone varchar(20),
    tipo char(1) not null default 'C',
    constraint tipo_usuario check (tipo IN ('C', 'P'))  -- cliente ou profissional
)