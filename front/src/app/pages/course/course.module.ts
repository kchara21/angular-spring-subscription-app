import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseRoutingModule } from './course-routing.module';
import { CourseComponent } from './course.component';
import { CoursesComponent } from './courses/courses.component';
import { CoursesModule } from './courses/courses.module';
import { ModalCoursesComponent } from './components/modal-courses/modal-courses.component';

@NgModule({
  declarations: [CourseComponent],
  imports: [CommonModule, CourseRoutingModule, CoursesModule],
})
export class CourseModule {}
