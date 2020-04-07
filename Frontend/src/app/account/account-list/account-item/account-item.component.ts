import { Component, Input, Output, EventEmitter } from '@angular/core';
import { AccountModel } from 'src/app/shared/models/account.model';
import { AccountInterface } from 'src/app/shared/models/interfaces/account';

@Component({
  selector: 'app-account-item',
  templateUrl: './account-item.component.html',
  styleUrls: ['./account-item.component.scss']
})
export class AccountItemComponent {
  /** Index number */
  @Input() index: number;

  /** Will hold the data of a single Account */
  @Input() accountData: AccountInterface;

  @Output()
  selectedAccountId: EventEmitter<number> = new EventEmitter();

  constructor(public accountModel: AccountModel) {}

  /**
   * Will notify the parent component (account-list.component.ts)
   * when an account has been selected, an pass the id value of the selected account
   */
  onAccountSelect() {
    this.selectedAccountId.emit(this.accountData.accountId);
  }
}
