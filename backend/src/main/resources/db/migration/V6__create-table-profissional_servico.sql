create table profissional_servico (
    profissional_id int not null
        references profissional(id) on delete cascade,
    servico_id int not null
        references servico(id) on delete cascade ,
    primary key (profissional_id, servico_id)
)