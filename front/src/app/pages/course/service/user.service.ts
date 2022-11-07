import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getUser(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.BASE_URL}/users/${userId}`);
  }

  getPendingUsers(): Observable<any> {
    return this.http.get<any>(`${environment.BASE_URL}/users/pending`);
  }

  acceptUser(userId: number): Observable<any> {
    return this.http.put<any>(
      `${environment.BASE_URL}/users/${userId}/accept`,
      null
    );
  }

  rejectUser(userId: number): Observable<any> {
    return this.http.delete<any>(`${environment.BASE_URL}/users/${userId}`);
  }

  suscribeToCourse(userId: number, courseId): Observable<any> {
    return this.http.put<any>(
      `${environment.BASE_URL}/users/${userId}/suscribe/${courseId}/courses`,
      null
    );
  }

  unsuscribeToCourse(userId: number, courseId): Observable<any> {
    return this.http.delete<any>(
      `${environment.BASE_URL}/users/${userId}/unsuscribe/${courseId}/courses`
    );
  }
}
