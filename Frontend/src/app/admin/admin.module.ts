import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AuthGuard } from '../shared/services/auth.guard.service';
import { AuthService } from '../shared/services/auth.service';
import { StorageService } from '../shared/services/storage.service';
import { AuthInterceptor } from '../shared/interceptor/http.interceptor';

/** Importing SharedModule in order to re-use certain components (from an external module) */
import { SharedComponentsModule } from '../shared/components/shared-components.module';

/** Module routing and avialable components (at the same module level) */
import {AdminRoutingModule} from './admin-routing.module';
import { EmployeeListComponent } from './employee/employee-list/employee-list.component';
import { EmployeeItemComponent } from './employee/employee-list/employee-item/employee-item.component';
import { EmployeeDetailsComponent } from './employee/employee-details/employee-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatFormFieldModule} from '@angular/material'
import {MatSelectModule} from '@angular/material'
import {MatCardModule, MatInputModule } from '@angular/material';
import { ApiService } from '../shared/services/api.service';


@NgModule({
  declarations: [EmployeeItemComponent, EmployeeListComponent, EmployeeDetailsComponent],

  imports: [CommonModule, HttpClientModule, AdminRoutingModule, FormsModule, ReactiveFormsModule,
  SharedComponentsModule, MatFormFieldModule, MatSelectModule, MatCardModule, MatInputModule],
  providers: [
    ApiService,
    AuthGuard,
    AuthService,
    StorageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
})
export class AdminModule {}
