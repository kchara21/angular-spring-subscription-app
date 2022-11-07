import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { UserResponse } from 'src/app/shared/models/user.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _user = new BehaviorSubject<UserResponse>(null!);

  constructor(private http: HttpClient, private _router: Router) {
    this.checkToken();
  }

  get user$(): Observable<UserResponse> {
    return this._user.asObservable();
  }

  get userValue(): UserResponse {
    return this._user.getValue();
  }

  private checkToken(): void {
    const user = JSON.parse(localStorage.getItem('user')!) || null;
    if (user) {
      this._user.next(user);
    }
  }

  login(authData: any): Observable<any | void> {
    return this.http
      .post<UserResponse>(`${environment.BASE_URL}/auth/login`, authData)
      .pipe(
        map((user: UserResponse) => {
          //save Token()
          this.saveLocalStorageUser(user);
          this._user.next(user);
          return user;
        })
      );
  }

  register(user: any): Observable<any> {
    return this.http.post(`${environment.BASE_URL}/auth/register`, user);
  }

  verify(email: string): Observable<any> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('email', email);

    return this.http.get(`${environment.BASE_URL}/auth/verify`, {
      params: queryParams,
    });
  }

  private saveLocalStorageUser(user: UserResponse): void {
    const { password, ...rest } = user;
    localStorage.setItem('user', JSON.stringify(rest));
  }

  logout(): void {
    localStorage.removeItem('user'); // TODO: AQUI REMOVEMOS EL TOKEN DEL USUARIO EN EL LOCAL STORAGE
    this._user.next(null!);
    this._router.navigate(['login']);
  }
}
