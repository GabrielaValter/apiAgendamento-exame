import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { MenuDashboard } from '../menu-dashboard/menu-dashboard';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [MenuDashboard, RouterModule, RouterOutlet],
    templateUrl: './home.html',
    styleUrl: './home.css',
})
export class Home {}
