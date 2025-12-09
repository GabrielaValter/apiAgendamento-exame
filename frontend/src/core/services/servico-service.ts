import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Servico } from '../models/servico';

@Injectable({
    providedIn: 'root',
})
export class ServicoService {
    private readonly baseUrl = `${env.apiUrl}/servicos`;
    constructor(private http: HttpClient) {}

    listar(): Observable<Servico[]> {
        return this.http.get<Servico[]>(this.baseUrl);
    }

    buscarPorId(id: number): Observable<Servico> {
        return this.http.get<Servico>(`${this.baseUrl}/${id}`);
    }

    criar(dto: {
        nome: string;
        descricao: string;
        valor: number;
    }): Observable<Servico> {
        return this.http.post<Servico>(this.baseUrl, dto);
    }

    atualizar(id: number, dto: {
        nome: string;
        descricao: string;
        valor: number;
    }): Observable<Servico> {
        return this.http.put<Servico>(`${this.baseUrl}/${id}`, dto);
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}
