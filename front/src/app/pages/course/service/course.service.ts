import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient) {}

  getCourses(userId: number): Observable<any> {
    return this.http.get<any>(
      `${environment.BASE_URL}/users/${userId}/courses`
    );
  }

  getSuscribedCourses(userId: number): Observable<any> {
    return this.http.get<any>(
      `${environment.BASE_URL}/users/${userId}/subscriptions`
    );
  }

  deleteCourse(courseId: number): Observable<any> {
    return this.http.delete<any>(`${environment.BASE_URL}/${courseId}/courses`);
  }

  saveCourse(userId: number, course: any): Observable<any> {
    return this.http.post(
      `${environment.BASE_URL}/users/${userId}/courses`,
      course
    );
  }

  updateCourse(courseId: number, course: any): Observable<any> {
    return this.http.put(`${environment.BASE_URL}/${courseId}/courses`, course);
  }
}
