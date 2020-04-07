import { Component, OnInit } from '@angular/core';
import { AccountModel } from 'src/app/shared/models/account.model';
import { ApiService } from 'src/app/shared/services/api.service';
import { AccountInterface } from 'src/app/shared/models/interfaces/account';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.scss']
})
export class AccountListComponent implements OnInit {
  /** Flag for letting know the user that something is loading/happening */
  isProcessing: boolean;

  //flag to see if add client button is selected
  addAccount: boolean = false;

  
  //flag to see if pay bill button is selected
  payBill: boolean = false;

  //flag to see if transfer money button is selected
  transferMoney: boolean = false;

  constructor(public accountModel: AccountModel, private apiService: ApiService) {}

  ngOnInit() {
    this.getAllAccounts();
  }

  getAllAccounts(reloadAccounts: boolean = false) {
    this.addAccount = false;
    if (!reloadAccounts) {
      /** Initialize the loading process */
      this.isProcessing = true;
      /** Clear the Account Model values (if it was instantiated before) */
      this.accountModel.clear();
    }
    this.apiService.getAccounts().subscribe(
      /** On Success: save the list of accounts in the account model */
      (data: Array<AccountInterface>) => (this.accountModel.all = data),
      /** On Error: log the error and end the loading process */
      (error: HttpErrorResponse) => {
        console.error(error);
        this.isProcessing = false;
      },
      /** End the loading process no matter what
       * at the end of all operations */
      () => (this.isProcessing = false)
    );
  }

  onAccountIdSelected(accountId: number) {
    this.accountModel.selectedAccountId = accountId;
    this.addAccount = false;
    this.payBill = false;
    this.transferMoney = false;
  }

  onAddAccount()
  {
    this.addAccount = true;
    this.payBill = false;
    this.transferMoney = false;
    console.log("button pressed");
  }

  onPayBill()
  {
    this.payBill = true;
    this.addAccount = false;
    this.transferMoney = false;
    console.log("button pressed");
  }

  onTransferMoney()
  {
    this.transferMoney = true;
    this.addAccount = false;
    this.payBill = false;
    console.log(this.transferMoney);
  }
}
