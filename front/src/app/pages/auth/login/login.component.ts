import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BaseFormUser } from 'src/app/shared/utils/base-form-user';
import Swal from 'sweetalert2';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  hide = true;
  private _subscription: Subscription = new Subscription();

  constructor(
    private _authService: AuthService,
    private _router: Router,
    public _loginForm: BaseFormUser
  ) {}

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  ngOnInit(): void {
    this._loginForm.baseForm.markAsUntouched();

    this._loginForm.baseForm.get('name').setValidators(null);
    this._loginForm.baseForm.get('lastname').setValidators(null);
    this._loginForm.baseForm.get('role').setValidators(null);

    this._loginForm.baseForm.get('email').setValue('');
    this._loginForm.baseForm.get('password').setValue('');
    this._loginForm.baseForm.updateValueAndValidity();
  }

  checkField(field: string): boolean {
    return this._loginForm.isValidField(field);
  }

  onLogin(): void {
    if (this._loginForm.baseForm.invalid) {
      return;
    }
    const formValue = this._loginForm.baseForm.value;
    this._subscription.add(
      this._authService.login(formValue).subscribe({
        next: (auth) => {
          if (auth.role == 'ROLE_ADMIN') {
            this._router.navigate(['courses/users']);
          } else {
            this._router.navigate(['/courses']);
          }
        },
        error: (err: any) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.error.message,
          });
        },
      })
    );
  }
}
