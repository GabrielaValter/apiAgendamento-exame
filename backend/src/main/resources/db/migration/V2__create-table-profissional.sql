create table profissional(
    id int primary key
    references usuario(id) on delete cascade, --extensão de usuário
    area varchar(60) not null,
    descricao text not null
)