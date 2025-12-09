import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { MenuRoot } from '../menu-root/menu-root';

@Component({
    selector: 'app-home-admin',
    standalone: true,
    imports: [MenuRoot, RouterModule, RouterOutlet],
    templateUrl: './home-admin.html',
    styleUrl: './home-admin.css',
})
export class HomeAdmin {}
