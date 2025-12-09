import { Usuario } from './usuario';
import { Profissional } from './profissional';
import { Servico } from './servico';

export type StatusAgendamento = 'PENDENTE' | 'CONFIRMADO' | 'CANCELADO' | 'CONCLUIDO';

export interface Agendamento {
    id?: number;
    data: string;
    status?: StatusAgendamento;
    observacao?: string;

    cliente: Usuario;
    profissional: Profissional;
    servico: Servico;
}
