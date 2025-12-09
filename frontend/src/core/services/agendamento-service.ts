import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable, of } from 'rxjs';
import { Agendamento } from '../models/agendamento';
import { AuthService } from './auth-service';

@Injectable({
    providedIn: 'root',
})
export class AgendamentoService {
    private readonly baseUrl = `${env.apiUrl}/agendamentos`;

    constructor(
        private http: HttpClient,
        private authService: AuthService
    ) {}

    criar(dto: {
        clienteId: number;
        profissionalId: number;
        servicoId: number;
        data: string; //yyyy-MM-dd
        observacao?: string;
    }): Observable<Agendamento> {
        return this.http.post<Agendamento>(this.baseUrl, dto);
    }

    listar(): Observable<Agendamento[]> {
        return this.http.get<Agendamento[]>(this.baseUrl);
    }

    buscarPorId(id: number): Observable<Agendamento> {
        return this.http.get<Agendamento>(`${this.baseUrl}/${id}`);
    }

    atualizarStatus(id: number, status: string): Observable<Agendamento> {
        return this.http.patch<Agendamento>(
            `${this.baseUrl}/${id}/status`,
            { status }
        );
    }

    listarPorCliente(clienteId: number): Observable<Agendamento[]> {
        return this.http.get<Agendamento[]>(
            `${this.baseUrl}/cliente/${clienteId}`
        );
    }

    listarPorProfissional(profissionalId: number): Observable<Agendamento[]> {
        return this.http.get<Agendamento[]>(
            `${this.baseUrl}/profissional/${profissionalId}`
        );
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }

    listarMinhasConsultas(): Observable<Agendamento[]> {
        const user = this.authService.getUser();

        if (user && user.id) {
            return this.listarPorCliente(user.id);
        }

        return this.listar();
    }
}
