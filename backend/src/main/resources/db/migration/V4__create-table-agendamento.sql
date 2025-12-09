create table agendamento(
    id serial primary key,
    data date not null,
    status varchar(10) not null default 'PENDENTE', -- pode ser pendente, confirmado ou cancelado
    observacao text,

    cliente_id int not null,
    profissional_id int not null,
    servico_id int not null,

    constraint status_agendamento check (status in ('PENDENTE', 'CONFIRMADO', 'CANCELADO', 'CONCLUIDO')),

    constraint fk_agendamento_cliente
        foreign key (cliente_id) references usuario(id) on delete restrict,
    constraint fk_agendamento_profissional
        foreign key (profissional_id) references profissional(id) on delete restrict,
    constraint fk_agendamento_servico
        foreign key (servico_id) references servico(id) on delete restrict,

    -- evita alguém marcar para atender a si mesmo
    constraint cliente_profissional_diferentes check (cliente_id <> profissional_id)
)