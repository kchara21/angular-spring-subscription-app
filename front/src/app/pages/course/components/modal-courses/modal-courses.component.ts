import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { BaseFormCourse } from '../../../../shared/utils/base-form-course';
import { CourseService } from '../../service/course.service';

enum Action {
  EDIT = 'edit',
  NEW = 'new',
}

@Component({
  selector: 'app-modal-courses',
  templateUrl: './modal-courses.component.html',
  styleUrls: ['./modal-courses.component.css'],
})
export class ModalCoursesComponent implements OnInit {
  actionTODO = Action.NEW;
  userId: number = null;
  statusCourse = ['active', 'inactive', 'pending'];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public courseForm: BaseFormCourse,
    public courseSvc: CourseService
  ) {}

  ngOnInit(): void {
    this.userId = this.data.userId;

    if (this.data?.course.hasOwnProperty('id')) {
      this.actionTODO = Action.EDIT;
      this.pathFormData();
      this.data.title = 'Editar Curso';
    } else {
      this.courseForm.baseForm.markAsUntouched();
      this.courseForm.baseForm.get('title').setValue('');
      this.courseForm.baseForm.get('description').setValue('');
      this.courseForm.baseForm.get('status').setValue('');
      this.courseForm.baseForm.updateValueAndValidity();
    }
  }

  onSaveCourse(): void {
    const formValue = this.courseForm.baseForm.value;
    if (this.actionTODO === Action.NEW) {
      this.courseSvc.saveCourse(this.userId, formValue).subscribe({
        next: (res) => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Curso Guardado!',
            showConfirmButton: false,
            timer: 1500,
          });
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
      });
    } else {
      const courseId = this.data?.course?.id;
      this.courseSvc.updateCourse(courseId, formValue).subscribe({
        next: (res) => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Curso Acualizado!',
            showConfirmButton: false,
            timer: 1000,
          });
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
      });
    }
  }

  checkField(field: string): boolean {
    return this.courseForm.isValidField(field);
  }

  private pathFormData(): void {
    this.courseForm.baseForm.patchValue({
      title: this.data?.course?.title,
      description: this.data?.course?.description,
      status: this.data?.course?.status,
    });
  }
}
