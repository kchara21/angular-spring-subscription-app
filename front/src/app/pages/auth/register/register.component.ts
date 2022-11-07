import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BaseFormUser } from 'src/app/shared/utils/base-form-user';
import Swal from 'sweetalert2';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  hide = true;
  private _subscription: Subscription = new Subscription();
  userRoles = ['ROLE_CONSUMER', 'ROLE_CREATOR'];

  constructor(
    private _router: Router,
    private _authService: AuthService,
    public _registerForm: BaseFormUser
  ) {}

  ngOnInit(): void {
    this._registerForm.baseForm.markAsTouched();

    this._registerForm.baseForm.get('name').setValue('');
    this._registerForm.baseForm.get('lastname').setValue('');
    this._registerForm.baseForm.get('role').setValue('');
    this._registerForm.baseForm.get('email').setValue('');
    this._registerForm.baseForm.get('password').setValue('');

    this._registerForm.baseForm.updateValueAndValidity();
  }

  onRegister(): void {
    if (this._registerForm.baseForm.invalid) return;

    const formValue = this._registerForm.baseForm.value;

    this._subscription.add(
      this._authService.register(formValue).subscribe({
        next: (res) => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Usuario Creado!',
            showConfirmButton: false,
            timer: 1500,
          }).then(() => this._router.navigate(['/login']));
        },
        error: (err) => {
          Swal.fire({
            position: 'center',
            icon: 'error',
            title: err.error.message,
            showConfirmButton: false,
            timer: 1500,
          });
        },
      })
    );
  }

  checkField(field: string): boolean {
    return this._registerForm.isValidField(field);
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }
}
