import { Usuario } from './usuario';
import { Servico } from './servico';

export interface Profissional {
    id?: number;
    usuario: Usuario;
    area: string;
    descricao: string;
    servicos?: Servico[];
}
