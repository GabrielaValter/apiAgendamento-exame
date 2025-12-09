import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Public } from './public/public';
import { Analise } from './analysis/analysis';

export const DASHBOARD_ROUTES: Routes = [
    {
        path: '',
        component: Home,
        children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: Public },
            { path: 'analise', component: Analise },
        ]
    }
];
