import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import Swal from 'sweetalert2';
import { CourseService } from '../service/course.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css'],
})
export class SubscriptionsComponent implements OnInit {
  user = JSON.parse(localStorage.getItem('user')!) || null;

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
  constructor(private courseSvc: CourseService, private userSvc: UserService) {}

  ngOnDestroy(): void {
    this.destroy$.next({});
    this.destroy$.complete();
  }

  loadCourses() {
    this.courseSvc.getSuscribedCourses(this.user.id).subscribe({
      next: (suscribedCourses) => {
        this.dataSource.data = suscribedCourses.content;
      },
    });
  }

  ngOnInit(): void {
    this.loadCourses();
  }

  onUnsubscribeCourse(courseId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Unsuscribe',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userSvc
          .unsuscribeToCourse(this.user.id, courseId)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: (res) => {
              Swal.fire('Desuscrito!', res?.['message'], 'success');
              this.loadCourses();
            },
            error: (err) => {
              Swal.fire('Ups!', err?.error?.['message'], 'warning');
              this.loadCourses();
            },
          });
      }
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }
}
