import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';

@Component({
    selector: 'app-menu-root',
    standalone: true,
    imports: [MatToolbarModule, MatButtonModule, RouterModule],
    templateUrl: './menu-root.html',
    styleUrl: './menu-root.css',
})
export class MenuRoot {

    constructor(private authService: AuthService) {}

    onLogout(): void {
        this.authService.logout();
    }
}
