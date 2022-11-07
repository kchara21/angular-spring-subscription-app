import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../service/user.service';
import { CourseService } from '../service/course.service';
import { MatTableDataSource } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { AuthService } from '../../auth/service/auth.service';
import { UserResponse } from 'src/app/shared/models/user.interface';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { ModalCoursesComponent } from '../components/modal-courses/modal-courses.component';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  user: any = null;

  displayedColumns: string[] = [
    'id',
    'title',
    'description',
    'status',
    'actions',
  ];
  dataSource = new MatTableDataSource();
  private destroy$ = new Subject<any>();

  @ViewChild(MatSort) sort!: MatSort;
  constructor(
    private courseSvc: CourseService,
    private _authSvc: AuthService,
    private _dialog: MatDialog,
    private userSvc: UserService
  ) {}

  ngOnDestroy(): void {
    this.destroy$.next({});
    this.destroy$.complete();
  }

  loadCourses(userId: number) {
    this.courseSvc.getCourses(userId).subscribe({
      next: (courses) => {
        this.dataSource.data = courses.content;
      },
    });
  }

  ngOnInit(): void {
    this._authSvc.user$.pipe(takeUntil(this.destroy$)).subscribe({
      next: (user: UserResponse) => {
        this.user = user;
      },
    });
    this.loadCourses(this.user.id);
  }

  onSuscribeCourse(courseId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Suscribe',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userSvc
          .suscribeToCourse(this.user.id, courseId)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: (res) => {
              Swal.fire('Suscrito!', res?.['message'], 'success');
              this.loadCourses(this.user.id);
            },
            error: (err) => {
              Swal.fire('Ups!', err?.error?.['message'], 'warning');
              this.loadCourses(this.user.id);
            },
          });
      }
    });
  }

  onDeleteCourse(courseId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'No podrás revertir el cambio',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '¡Sí, eliminar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.courseSvc
          .deleteCourse(courseId)
          .pipe(takeUntil(this.destroy$))
          .subscribe((res) => {
            Swal.fire('¡Eliminado!', res?.['message'], 'success');
            this.loadCourses(courseId);
          });
      }
    });
  }

  onOpenModalCourse(course = {}): void {
    this._dialog
      .open(ModalCoursesComponent, {
        height: '400px',
        width: '600px',
        hasBackdrop: false,
        data: { title: 'Nuevo Curso', course, userId: this.user?.id },
      })
      .afterClosed()
      .subscribe((res) => {
        this.loadCourses(this.user?.id);
      });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }
}
