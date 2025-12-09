import { Routes } from '@angular/router';
import { HomeAdmin } from './home-admin/home-admin';
import { HomeCard } from './home-card/home-card';
import { Register } from './register/register';
import { Users } from './users/users';
import { authGuard } from '../../core/security/auth-guard';
import { Consultas } from './consultation/consultation';

export const ROOT_ROUTES: Routes = [
    {
        path: '',
        component: HomeAdmin,
        canActivate: [authGuard],
        children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: HomeCard },
            { path: 'users', component: Users },
            { path: 'register', component: Register },
            { path: 'consultas', component: Consultas },
        ],
    },
];
