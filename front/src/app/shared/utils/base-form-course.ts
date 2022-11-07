import { Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class BaseFormCourse {
  errorMessage = null;
  constructor(private fb: FormBuilder) {}

  baseForm = this.fb.group({
    title: ['', [Validators.required, Validators.minLength(6)]],
    description: ['', [Validators.required, Validators.minLength(2)]],
    status: ['', [Validators.required]],
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
        required: 'Campo requerido',
        minlength: `Debe tener minimo ${minLength} caracteres`,
      };
      const errorKey = Object.keys(errors).find(Boolean);
      this.errorMessage = messages[errorKey];
    }
  }
}
