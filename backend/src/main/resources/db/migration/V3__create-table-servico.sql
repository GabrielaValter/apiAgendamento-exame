create table servico(
    id serial not null primary key,
    nome varchar(60) not null,
    descricao text not null ,
    valor numeric(10,2) not null check (valor >= 0)
)