import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../../core/services/auth-service';
import {Router, RouterModule} from '@angular/router';

@Component({
    selector: 'app-login',
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatSnackBarModule,
        MatIconModule,
        RouterModule
    ],
    templateUrl: './login.html',
    styleUrl: './login.css',
})
export class Login {
    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private snack: MatSnackBar,
        private authService: AuthService,
        private router: Router
    ) {
        this.form = this.fb.group({
            email: ['', [Validators.required, Validators.email]],
            senha: ['', [Validators.required]]
        });
    }

    submit() {
        if (this.form.invalid) {
            this.snack.open('Preencha corretamente os dados', 'OK', { duration: 2000 });
            return;
        }

        const { email, senha } = this.form.value;
        this.authService.login(email, senha).subscribe({
            next: () => {
                this.router.navigate(['/home']);
            },
            error: () => {
                this.snack.open('Credenciais inválidas', 'OK', { duration: 2000 });
            }
        });
    }
}
