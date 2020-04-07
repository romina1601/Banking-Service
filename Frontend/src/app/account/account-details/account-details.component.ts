import { Component, Input, Output, OnChanges, SimpleChange, EventEmitter } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

import { AccountInterface } from 'src/app/shared/models/interfaces/account';

import { AccountModel } from 'src/app/shared/models/account.model';
import { ApiService } from 'src/app/shared/services/api.service';
import {  ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { AccountInterface2 } from 'src/app/shared/models/interfaces/account2';
import { ClientInterface } from 'src/app/shared/models/interfaces/client';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.scss']
})
export class AccountDetailsComponent implements OnChanges {
  @Input()
  accountId: number;

  @Input()
  clientSsn: string;
  
  @Input()
  addAccount: boolean;

  @Input()
  payBill: boolean;

  @Input()
  transferMoney: boolean;

  @Output()
  onSaveSuccess: EventEmitter<boolean> = new EventEmitter();

  /** Account data */
   accountData: AccountInterface;
  /** Copy of the initial account data */
   _accountData: AccountInterface;
  /** Flag for letting know the user that save is in progress */
   isSaving: boolean;
  /** Flag for letting know the user that remove is in progress */
   isRemoving: boolean;

  /** Flag for letting know the user that inserting an account is in progress */
  isInserting: boolean;

  /** For adding new client */
  clientSsnForm = new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(13), Validators.pattern('[0-9]+')]);
  accountTypeForm = new FormControl();
  accountTypes: Array<string> = ["CREDIT", "SAVINGS", "EXPENSES"];
  accountTypeString: string;
  balanceForm = new FormControl('', [Validators.required, Validators.min(0)]);
  /** For paying bills */
  billDetailsForm = new FormControl('', [Validators.required]);
  amountForm = new FormControl('', [Validators.required, Validators.min(0)]);
  clients: Array<ClientInterface>;
  accounts : Array<AccountInterface>;
  fromAccountForm = new FormControl('', [Validators.required]);
  fromAccountId: number;
  /** For transfer money */
  toAccountForm = new FormControl('', [Validators.required, Validators.pattern('[0-9]+')]);
  


  private shortAccountData: AccountInterface2;
  

  constructor(public accountModel: AccountModel, private apiService: ApiService, private toastr: ToastrService) {}

  /**
   * This is an Angular method
   * (one of the lifcecycle component hooks: https://angular.io/guide/lifecycle-hooks)
   * Will detect changes on the @Input data, e.g when user select a diferent account from the list
   */
  ngOnChanges(changes: { accountId: SimpleChange, addAccount: SimpleChange, payBill: SimpleChange, transferMoney: SimpleChange }) {
    const { accountId, addAccount, payBill, transferMoney } = changes;

    //console.log(addAccount);
    console.log(transferMoney);



    if (!accountId || !accountId.currentValue) this.clearComponentData();

    if (accountId && accountId.currentValue && accountId.currentValue !== accountId.previousValue) {
      /** If accountId changes on selection, get the selected account details */
      this.clearForm();
      this.getAccountDetailsData(accountId.currentValue);
    }

    if (payBill && payBill.currentValue != payBill.previousValue)
    {
      this.clientSsnForm.valueChanges.subscribe(data => {
        this.clearFormPayBill();
        //console.log(this.clientSsnForm.value);
        this.apiService.getAccounts().subscribe(
          /** On Success: save the list of clients in the client model */
          (data2: Array<AccountInterface>) => {this.accounts = data2.filter(account => account.clientSsn===this.clientSsnForm.value);},
          /** On Error: log the error and end the loading process */
          (error: HttpErrorResponse) => {
              console.error(error);
          }
      );
       }); 
    }

    if (transferMoney && transferMoney.currentValue != transferMoney.previousValue)
    {
      this.clearFormTransferMoney();
      this.clientSsnForm.valueChanges.subscribe(data => {
        //console.log(this.clientSsnForm.value);
        this.apiService.getAccounts().subscribe(
          /** On Success: save the list of clients in the client model */
          (data2: Array<AccountInterface>) => {this.accounts = data2.filter(account => account.clientSsn===this.clientSsnForm.value);},
          /** On Error: log the error and end the loading process */
          (error: HttpErrorResponse) => {
              console.error(error);
          }
      );
       }); 
    }
  }

  /**
   * Requesting account details data through the API Service
   * @param accountId  {number}
   */
  getAccountDetailsData(accountId: number): void {
    this.apiService.getAccountDetails(accountId).subscribe(
      (data: AccountInterface) => {
        /** Saving the obtained account data into a variable */
        this.accountData = data;
        /** Making copy of the initial account data (for comparing purpose only) */
        this._accountData = Object.assign({}, this.accountData);
      },
      (error: HttpErrorResponse) => console.error(error)
    );
  }

  /**
   * Will submit the account data if has been changed
   * This method is it bind to the `Save Account` button
   */
  onAccountSave(): void {
    /** Initialize the isSaving flag */
    this.isSaving = true;
    /** Trigger the saving method from the API Service passing the accountData*/
    this.apiService.saveAccountDetails(this.accountData).subscribe(
      /** On Success */
      (data: AccountInterface) => {
        /** Update the copy of the initial account data */
        this._accountData = data;
        /** Notify the parent component to refresh the account list */
        this.onSaveSuccess.emit(true);
        /** Notify the user with a successful message */
        this.toastr.success('Account details updated!');
      },
      /** On Error */
      (error: HttpErrorResponse) => {
        /** Notify the user about the error */
        this.toastr.error(error.message);
        /** End the isSaving flag */
        this.isSaving = false;
      },
      /** End the isSaving flag */
      () => (this.isSaving = false)
    );
  }

  /**
   * Will remove the account data through the API service
   * This method is it bind to the `Delete` button
   */
  onAccountRemove(): void {
    /** Initialize the isRemoving flag */
    this.isRemoving = true;
    /** Trigger the saving method from the API Service passing the accountId*/
    this.apiService.deleteAccount(this.accountData.accountId).subscribe(
      () => {
        /** End the isRemoving flag */
        this.isRemoving = false;
        /** Clear the component data */
        this.clearComponentData();
        /** Notify the parent component to refresh the account list */
        this.onSaveSuccess.emit(true);
        /** Notify the user with a successful message */
        this.toastr.success('Account was removed!');
      },
      (error: HttpErrorResponse) => {
        /** Notify the user about the error */
        this.toastr.error(error.message);
        /** End the isRemoving flag */
        this.isRemoving = false;
      }
    );
  }

  /**
   * Will submit the client data if has been changed
   * This method is it bind to the `Save New Account` button
   * in client-details.component.html
   */
  onNewAccountSave(): void {
    /** Initialize the isSaving flag */
    this.isInserting = true;
    this.shortAccountData={
      clientSsn: this.clientSsnForm.value,
      balance: this.balanceForm.value,
      accountType: this.accountTypeString
    };

    /** Trigger the saving method from the API Service passing the clientData*/
    this.apiService.insertAccountDetails(this.shortAccountData).subscribe(
      
      (data: AccountInterface) => {
        this.accountData = Object.assign(data);

        this.apiService.addAccountToClient(this.clientSsnForm.value, this.accountData.accountId).subscribe(
          /** On Success */
          (response: any) => {
            this.onSaveSuccess.emit(true);
            this.toastr.success("Account added to client");
            this.clearForm();
          },

          (error: HttpErrorResponse) => {
            this.toastr.error(error.message);
            this.isInserting = false;
            this.clearForm();
            this.accountData = undefined;
          },

          /** End the isSaving flag */
          () => (this.isInserting = false)
        );

      },

      /** On Error */
      (error: HttpErrorResponse) => {
        /** Notify the user about the error */
        this.toastr.error(error.message);
        /** End the isSaving flag */
        this.isInserting = false;
      }

    );

  }

  /**
   * Will pay a bill and update the amount of the selected account
   * This method is it bind to the `Pay Bill` button at bottom of form
   */
  onPayBill() {
    /** Initialize the isSaving flag */
    this.isSaving = true;
    console.log(this.fromAccountForm.value);
    /** Trigger the saving method from the API Service passing the accountData*/
    this.apiService.payBill(this.clientSsnForm.value, this.billDetailsForm.value, this.amountForm.value, this.fromAccountForm.value.accountId).subscribe(
        /** On Success */
        (data: any) => {
            /** Notify the parent component to refresf the account list */
            this.onSaveSuccess.emit(true);
            /** Notify the user with a successful message */
            this.toastr.success('Bill has been payed, yay');
        },
        /** On Error */
        (error: HttpErrorResponse) => {
            /** Notify the user about the error */
            this.toastr.error(error.message);
            /** End the isSaving flag */
            this.isSaving = false;
        },
        /** End the isSaving flag */
        () => (this.isSaving = false)
    );
}

  /**
   * Will pay a bill and update the amount of the selected account
   * This method is it bind to the `Transfer Money` button at bottom of form
   */
  onTransferMoney() {
    /** Initialize the isSaving flag */
    this.isSaving = true;
    console.log(this.fromAccountForm.value);
    /** Trigger the saving method from the API Service passing the accountData*/
    this.apiService.transferMoney(this.clientSsnForm.value, this.fromAccountForm.value.accountId, this.toAccountForm.value, this.amountForm.value).subscribe(
        /** On Success */
        (data: any) => {
            /** Notify the parent component to refresf the account list */
            this.onSaveSuccess.emit(true);
            /** Notify the user with a successful message */
            this.toastr.success('You transfered money, you are now poor');
        },
        /** On Error */
        (error: HttpErrorResponse) => {
            /** Notify the user about the error */
            this.toastr.error(error.message);
            /** End the isSaving flag */
            this.isSaving = false;
        },
        /** End the isSaving flag */
        () => (this.isSaving = false)
    );
}

  clearForm()
  {
    this.clientSsnForm.setValue(null);
    this.accountTypeForm.setValue(null);
    this.balanceForm.setValue(null);
  }
  clearForm2()
  {
    this.clientSsnForm.reset();
    this.accountTypeForm.reset();
    this.balanceForm.reset();
  }

  clearFormPayBill()
  {
    this.clientSsnForm.reset();
    this.fromAccountForm.reset();
    this.billDetailsForm.reset();
    this.amountForm.reset();
  }

  clearFormTransferMoney()
  {
    this.clientSsnForm.reset();
    this.fromAccountForm.reset();
    this.toAccountForm.reset();
    this.amountForm.reset();
  }

  fromAccountSelect(value: AccountInterface) {
    this.fromAccountId=value.accountId;
  }

  accountTypeSelect(value: string) {
    this.accountTypeString=value;
  }

  getSsnErrorMessage() {
    return this.clientSsnForm.hasError('required') ? 'You must enter a value' :
        this.clientSsnForm.hasError('minlength') ? 'SSN is too short; please eneter a 13-digit value' :
        this.clientSsnForm.hasError('maxlength') ? 'SSN is too long; please eneter a 13-digit value' :
        this.clientSsnForm.hasError('pattern') ? 'SSN can contain only numeric characters' :
            '';
  }

  getBalanceErrorMessage() {
    return this.balanceForm.hasError('required') ? 'You must enter a value' :
        this.balanceForm.hasError('min') ? 'You must enter a positive number' :
            '';
  }

  getBillDetailsErrorMessage() {
    return this.billDetailsForm.hasError('required') ? 'You must enter a value' :
            '';
  }

  getAmountErrorMessage() {
    return this.amountForm.hasError('required') ? 'You must enter a value' :
           this.amountForm.hasError('min') ? 'You must enter a positive number' :
            '';
  }

  getToAccountErrorMessage() {
    return this.toAccountForm.hasError('required') ? 'You must enter a value' :
        this.toAccountForm.hasError('pattern') ? 'You must enter only digits' :
            '';
  }


  /**
   * Will determinate if the data has been changed or not
   * Buttons will remaing disabled if not change has happened
   */
  hasAccountDataChanged(): boolean {
    return JSON.stringify(this.accountData) !== JSON.stringify(this._accountData);
  }

  /** This method will clear the `accountData` value and `_accountData` copy value */
  clearComponentData(): void {
    this.accountData = undefined;
    this._accountData = undefined;
  }
}
