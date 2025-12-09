import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
    providedIn: 'root',
})
export class UsuarioService {
    private readonly baseUrl = `${env.apiUrl}/usuarios`;
    constructor(private http: HttpClient) {}

    listar(): Observable<Usuario[]> {
        return this.http.get<Usuario[]>(this.baseUrl);
    }

    buscarPorId(id: number): Observable<Usuario> {
        return this.http.get<Usuario>(`${this.baseUrl}/${id}`);
    }

    criar(usuario: {
        nome: string;
        email: string;
        senha: string;
        telefone: string;
        tipo: string;
    }): Observable<Usuario> {
        return this.http.post<Usuario>(this.baseUrl, usuario);
    }

    atualizar(id: number, usuario: {
        nome: string;
        email: string;
        senha: string;
        telefone: string;
        tipo: string;
    }): Observable<Usuario> {
        return this.http.put<Usuario>(`${this.baseUrl}/${id}`, usuario);
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}
