import { Component } from '@angular/core';
import { NgFor } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
    selector: 'app-public',
    standalone: true,
    imports: [MatCardModule, NgFor],
    templateUrl: './public.html',
    styleUrl: './public.css',
})
export class Public {
    cards = [
        { title: 'Total de Agendamentos', value: '32', desc: 'Agendamentos cadastrados no sistema' },
        { title: 'Agendamentos de Hoje', value: '5', desc: 'Consultas marcadas para hoje' },
        { title: 'Serviços Ativos', value: '4', desc: 'Tipos de serviços disponíveis' },
        { title: 'Profissionais', value: '3', desc: 'Profissionais cadastrados' },
        { title: 'Clientes', value: '10', desc: 'Clientes com cadastro ativo' },
        { title: 'Última Atualização', value: 'Agora', desc: 'Dados atualizados recentemente' }
    ];
}
