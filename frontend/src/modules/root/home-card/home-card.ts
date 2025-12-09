import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { NgIf } from '@angular/common';

import { AuthService } from '../../../core/services/auth-service';
import { Usuario } from '../../../core/models/usuario';

@Component({
    selector: 'app-home-card',
    standalone: true,
    imports: [
        MatCardModule,
        MatIconModule,
        MatDividerModule,
        NgIf
    ],
    templateUrl: './home-card.html',
    styleUrl: './home-card.css',
})
export class HomeCard {

    user: Usuario | null;

    constructor(private authService: AuthService) {
        this.user = this.authService.getUser();
    }
}
