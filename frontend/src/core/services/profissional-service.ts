import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Profissional } from '../models/profissional';
import { Servico } from '../models/servico';

@Injectable({
    providedIn: 'root',
})
export class ProfissionalService {
    private readonly baseUrl = `${env.apiUrl}/profissionais`;
    constructor(private http: HttpClient) {}

    criar(dto: {
        usuarioId: number;
        area: string;
        descricao: string;
    }): Observable<Profissional> {
        return this.http.post<Profissional>(this.baseUrl, dto);
    }

    listar(): Observable<Profissional[]> {
        return this.http.get<Profissional[]>(this.baseUrl);
    }

    buscarPorId(id: number): Observable<Profissional> {
        return this.http.get<Profissional>(`${this.baseUrl}/${id}`);
    }

    atualizar(id: number, dto: {
        usuarioId: number;
        area: string;
        descricao: string;
    }): Observable<Profissional> {
        return this.http.put<Profissional>(`${this.baseUrl}/${id}`, dto);
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }

    vincularServico(profissionalId: number, servicoId: number): Observable<void> {
        return this.http.post<void>(
            `${this.baseUrl}/${profissionalId}/servicos/${servicoId}`,
            {}
        );
    }

    listarServicos(profissionalId: number): Observable<Servico[]> {
        return this.http.get<Servico[]>(
            `${this.baseUrl}/${profissionalId}/servicos`
        );
    }

    desvincularServico(profissionalId: number, servicoId: number): Observable<void> {
        return this.http.delete<void>(
            `${this.baseUrl}/${profissionalId}/servicos/${servicoId}`
        );
    }
}
