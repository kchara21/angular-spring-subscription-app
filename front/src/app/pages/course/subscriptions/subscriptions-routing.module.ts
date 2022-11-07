import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubscriptionsComponent } from './subscriptions.component';

const routes: Routes = [{ path: '', component: SubscriptionsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SubscriptionsRoutingModule {}
