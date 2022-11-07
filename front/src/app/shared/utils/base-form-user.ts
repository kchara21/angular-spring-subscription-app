import { Injectable } from '@angular/core';
import { AuthService } from '../../pages/auth/service/auth.service';
import {
  AbstractControl,
  FormBuilder,
  ValidationErrors,
  Validators,
} from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class BaseFormUser {
  errorMessage = null;
  constructor(private fb: FormBuilder, private authSvc: AuthService) {}

  baseForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(3)]],
    lastname: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(5)]],
    role: [''],
  });

  isValidField(field: string): boolean {
    this.getErrorMessage(field);
    return (
      (this.baseForm.get(field)?.touched || this.baseForm.get(field)?.dirty)! &&
      !this.baseForm.get(field)?.valid
    );
  }

  private getErrorMessage(field: string): void {
    const { errors } = this.baseForm.get(field)!;
    if (errors) {
      const minLength = errors?.['minlength']?.requiredLength;

      const messages = {
        required: 'Debe ingresar un valor',
        pattern: 'No es un email valido',
        minlength: `Debe tener minimo ${minLength} caracteres`,
      };
      const errorKey = Object.keys(errors).find(Boolean);
      this.errorMessage = messages[errorKey];
    }
  }
}
