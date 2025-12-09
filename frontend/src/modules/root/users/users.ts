import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { UsuarioService } from '../../../core/services/usuario-service';
import { Usuario } from '../../../core/models/usuario';

@Component({
    selector: 'app-users',
    standalone: true,
    imports: [CommonModule, MatTableModule, MatCardModule],
    templateUrl: './users.html',
    styleUrl: './users.css',
})
export class Users implements OnInit {

    displayedColumns = ['id', 'nome', 'email', 'tipo', 'telefone'];
    usuarios: Usuario[] = [];

    constructor(private service: UsuarioService) {}

    ngOnInit(): void {
        this.service.listar().subscribe({
            next: (data) => {
                this.usuarios = data;
            },
            error: (err) => {
                console.error('Erro ao carregar usuários', err);
            }
        });
    }
}
