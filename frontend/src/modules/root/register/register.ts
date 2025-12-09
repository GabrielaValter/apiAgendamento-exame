import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../core/services/usuario-service';

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule,
        MatSnackBarModule,
        MatIconModule,
        RouterModule
    ],
    templateUrl: './register.html',
    styleUrl: './register.css',
})
export class Register {

    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private usuarioService: UsuarioService,
        private snack: MatSnackBar,
        private router: Router
    ) {
        this.form = this.fb.group({
            nome: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.email]],
            senha: ['', [Validators.required, Validators.minLength(3)]],
            telefone: ['']
        });
    }

    onSubmit(): void {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            this.snack.open('Preencha os dados corretamente.', 'OK', { duration: 3000 });
            return;
        }

        const { nome, email, senha, telefone } = this.form.value;

        const payload = {
            nome,
            email,
            senha,
            telefone,
            tipo: 'C'   //cliente
        };

        this.usuarioService.criar(payload).subscribe({
            next: () => {
                this.snack.open('Usuário cadastrado com sucesso!', 'OK', { duration: 3000 });
                this.router.navigate(['/login']);
            },
            error: (err) => {
                console.error('Erro ao cadastrar usuário:', err); // ✅ veja o erro no console
                let msg = 'Erro ao cadastrar usuário!';

                if (err?.error?.message) {
                    msg = err.error.message;
                }

                this.snack.open(msg, 'OK', { duration: 4000 });
            }
        });
    }
}
