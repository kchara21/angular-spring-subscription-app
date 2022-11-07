import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SubscriptionsRoutingModule } from './subscriptions-routing.module';
import { SubscriptionsComponent } from './subscriptions.component';
import { MaterialModule } from '../../../material/material.module';

@NgModule({
  declarations: [SubscriptionsComponent],
  imports: [CommonModule, SubscriptionsRoutingModule, MaterialModule],
})
export class SubscriptionsModule {}
