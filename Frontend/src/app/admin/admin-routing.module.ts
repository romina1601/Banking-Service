import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '../shared/services/auth.guard.service';

import { EmployeeListComponent } from './employee/employee-list/employee-list.component';

const routes: Routes = [{ path: '', component: EmployeeListComponent, 
canActivate: [AuthGuard] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

//export default class AdminRoutingModule {}
export class AdminRoutingModule{}