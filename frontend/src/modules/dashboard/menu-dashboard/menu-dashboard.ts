import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-menu-dashboard',
    standalone: true,
    imports: [MatToolbarModule, MatButtonModule, RouterModule],
    templateUrl: './menu-dashboard.html',
    styleUrl: './menu-dashboard.css',
})
export class MenuDashboard {}
