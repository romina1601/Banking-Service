import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

/** Necessary services and interceptors for this module to work properly */
import { AuthInterceptor } from '../shared/interceptor/http.interceptor';
import { ApiService } from '../shared/services/api.service';
import { AuthGuard } from '../shared/services/auth.guard.service';
import { AuthService } from '../shared/services/auth.service';
import { StorageService } from '../shared/services/storage.service';

/** Importing SharedModule in order to re-use certain components (from an external module) */
import { SharedComponentsModule } from '../shared/components/shared-components.module';

/** Module routing and avialable components (at the same module level) */
import { AccountRoutingModule } from './account-routing.module';
import { AccountListComponent } from './account-list/account-list.component';
import { AccountItemComponent } from './account-list/account-item/account-item.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatFormFieldModule} from '@angular/material'
import {MatSelectModule} from '@angular/material'
import {MatCardModule, MatInputModule } from '@angular/material';

@NgModule({
  declarations: [AccountListComponent, AccountItemComponent, AccountDetailsComponent],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, AccountRoutingModule, SharedComponentsModule,  MatFormFieldModule, MatSelectModule, MatCardModule, MatInputModule],
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
export class AccountModule {}
