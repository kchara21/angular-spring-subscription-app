import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import Swal from 'sweetalert2';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'name',
    'lastname',
    'email',
    'role',
    'actions',
  ];

  dataSource = new MatTableDataSource();
  private destroy$ = new Subject<any>();

  @ViewChild(MatSort) sort!: MatSort;
  constructor(private userSvc: UserService) {}

  ngOnDestroy(): void {
    this.destroy$.next({});
    this.destroy$.complete();
  }

  loadUsersPendingApproval() {
    this.userSvc.getPendingUsers().subscribe({
      next: (pendingUsers) => {
        this.dataSource.data = pendingUsers.content;
      },
      error: (err) => {
        this.dataSource.data = null;
        Swal.fire(err?.error?.['message']);
      },
    });
  }

  ngOnInit(): void {
    this.loadUsersPendingApproval();
  }

  onAceptUser(userId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'No podrás revertir el cambio',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '¡Sí, aceptar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userSvc
          .acceptUser(userId)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: (res) => {
              Swal.fire('¡Aceptado!', res?.['message'], 'success');
              this.loadUsersPendingApproval();
            },
            error: (err) => {
              Swal.fire(err?.error?.['message']);
            },
          });
      }
    });
  }

  onRejectUser(userId: number): void {
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
        this.userSvc
          .rejectUser(userId)
          .pipe(takeUntil(this.destroy$))
          .subscribe((res) => {
            Swal.fire('¡Eliminado!', res?.['message'], 'success');
            this.loadUsersPendingApproval();
          });
      }
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }
}
