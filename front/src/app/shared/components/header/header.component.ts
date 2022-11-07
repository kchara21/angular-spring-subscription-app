import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/pages/auth/service/auth.service';
import { UserResponse } from '../../models/user.interface';
import { UtilsService } from '../../service/utils.service';
import { UserService } from '../../../pages/course/service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isConsumer: any = null;
  isLogged = false;

  private _destroy$ = new Subject<any>();
  @Output() toggleSidenav = new EventEmitter<void>();

  constructor(
    private _authSvc: AuthService,
    private utilsSvc: UtilsService,
    private _router: Router
  ) {}

  ngOnDestroy(): void {
    this._destroy$.next({});
    this._destroy$.complete();
  }

  ngOnInit(): void {
    this._authSvc.user$.pipe(takeUntil(this._destroy$)).subscribe({
      next: (user: UserResponse) => {
        this.isConsumer = user?.role;
        if (this.isConsumer) {
          this.isLogged = true;
        } else {
          this.isLogged = false;
        }
      },
    });
  }

  onToggleSidenav(): void {
    this.toggleSidenav.emit();
  }

  onLogout(): void {
    this.isLogged = false;
    this._authSvc.logout();
    this.utilsSvc.openSidebar(false);
  }
}
