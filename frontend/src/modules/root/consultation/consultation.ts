import { Component, OnInit } from '@angular/core';
import { NgIf, NgFor, DatePipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { AgendamentoService } from '../../../core/services/agendamento-service';
import { Agendamento } from '../../../core/models/agendamento';

@Component({
    selector: 'app-consultas',
    standalone: true,
    imports: [
        NgIf,
        DatePipe,
        MatCardModule,
        MatTableModule,
        MatChipsModule,
        MatIconModule,
        MatProgressSpinnerModule
    ],
    templateUrl: './consultation.html',
    styleUrl: './consultation.css',
})
export class Consultas implements OnInit {

    displayedColumns = ['data', 'profissional', 'servico', 'status'];
    consultas: Agendamento[] = [];
    loading = false;

    constructor(private agendamentoService: AgendamentoService) {}

    ngOnInit(): void {
        this.carregarConsultas();
    }

    carregarConsultas(): void {
        this.loading = true;
        this.agendamentoService.listarMinhasConsultas().subscribe({
            next: (lista) => {
                this.consultas = lista;
                this.loading = false;
            },
            error: (err) => {
                console.error('Erro ao carregar consultas', err);
                this.loading = false;
            }
        });
    }

    corStatus(status: string): 'primary' | 'accent' | 'warn' {
        switch (status) {
            case 'CONFIRMADO': return 'primary';
            case 'PENDENTE':   return 'accent';
            case 'CANCELADO':  return 'warn';
            case 'CONCLUIDO':  return 'primary';
            default:           return 'accent';
        }
    }
}
