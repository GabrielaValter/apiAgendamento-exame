import { Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Usuario } from '../models/usuario';
import { env } from '../../environment/environment';
import { Observable, of, throwError, delay, tap } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private readonly TOKEN_KEY = 'auth_token';
    private readonly USER_KEY  = 'auth_user';

    constructor(
        private router: Router,
        private route: ActivatedRoute
    ) {}

    login(email: string, senha: string): Observable<string> {
        if (!email || !senha) {
            return throwError(() => new Error('E-mail e senha são obrigatórios.'));
        }

        const payload = {
            sub: email,
            TIPO: 'C'
        };

        const token = this.generateJwt(payload);

        return of(token).pipe(
            delay(300),
            tap(t => this.setToken(t))
        );
    }

    logout(): void {
        localStorage.removeItem(this.TOKEN_KEY);
        localStorage.removeItem(this.USER_KEY);
        this.router.navigate(['/login']);
    }

    setToken(token: string): void {
        localStorage.setItem(this.TOKEN_KEY, token);
        const user = this.userFromToken(token);
        if (user) {
            localStorage.setItem(this.USER_KEY, JSON.stringify(user));
        }
    }

    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    isLogged(): boolean {
        return !!this.getToken();
    }

    getUser(): Usuario | null {
        const raw = localStorage.getItem(this.USER_KEY);
        if (!raw) return null;

        try {
            return JSON.parse(raw) as Usuario;
        } catch {
            return null;
        }
    }

    redirect(): void {
        this.route.queryParams.subscribe(params => {
            const returnUrl = params['returnUrl'] || '/home';
            this.router.navigateByUrl(returnUrl);
        });
    }

    private generateJwt(payload: any): string {
        const header = {
            alg: 'HS256',
            typ: 'JWT'
        };

        const base64Header  = btoa(JSON.stringify(header));
        const base64Payload = btoa(JSON.stringify(payload));

        // assinatura só pra ter o formato header.payload.signature
        return `${base64Header}.${base64Payload}.signature`;
    }

    private userFromToken(token: string): Usuario | null {
        try {
            const parts = token.split('.');
            if (parts.length < 2) return null;

            const payloadJson = atob(parts[1]);
            const payload = JSON.parse(payloadJson);

            const user: Usuario = {
                id: undefined,
                uuid: undefined,
                nome: payload.nome ?? '',
                email: payload.sub ?? '',
                telefone: '',
                tipo: payload.TIPO ?? 'C'
            };

            return user;
        } catch (e) {
            console.error('Erro ao decodificar token:', e);
            return null;
        }
    }
}
