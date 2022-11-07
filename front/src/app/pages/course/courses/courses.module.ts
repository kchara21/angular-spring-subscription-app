import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoursesRoutingModule } from './courses-routing.module';
import { CoursesComponent } from './courses.component';
import { MaterialModule } from '../../../material/material.module';
import { ModalCoursesComponent } from '../components/modal-courses/modal-courses.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [CoursesComponent, ModalCoursesComponent],
  imports: [
    CommonModule,
    CoursesRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [CoursesComponent],
})
export class CoursesModule {}
