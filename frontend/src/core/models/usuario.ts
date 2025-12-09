export interface Usuario {
    id?: number;
    uuid?: string;
    nome: string;
    email: string;
    senha?: string;
    telefone?: string;
    tipo: 'C' | 'P';
}
