import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () =>
            import('../modules/root/login/login').then(m => m.Login),
    },
    {
        path: 'register',
        loadComponent: () =>
            import('../modules/root/register/register').then(m => m.Register),
    },
    {
        path: '',
        loadChildren: () =>
            import('../modules/root/root.routes').then(m => m.ROOT_ROUTES),
    },
    {
        path: '**',
        redirectTo: 'login',
    },
];
