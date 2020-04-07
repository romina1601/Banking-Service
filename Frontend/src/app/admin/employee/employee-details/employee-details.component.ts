import { Component, Input, Output, OnChanges, SimpleChange, EventEmitter } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

import { EmployeeInterface } from 'src/app/shared/models/interfaces/employee';

import { EmployeeModel } from 'src/app/shared/models/employee.model';
import { ApiService } from 'src/app/shared/services/api.service';

import {FormControl, Validators} from '@angular/forms'
import { ActivityInterface } from 'src/app/shared/models/interfaces/activity';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.scss']
})
export class EmployeeDetailsComponent implements OnChanges {
  
  /** Comes from employee-list.component.html */
  @Input()
  employeeId: string;

  @Input()
  addEmployee: boolean;

  @Input()
  generateReport: boolean;

  @Output()
  onSaveSuccess: EventEmitter<boolean> = new EventEmitter();

  /** Employee data */
  employeeData: EmployeeInterface;
  /** Copy of the initial employee data */
  _employeeData: EmployeeInterface;
  /** Flag for letting know the user that save is in progress */
  /* Used in employee-details.component.html*/
  isSaving: boolean;
  /** Flag for letting know the user that remove is in progress */
  /* Used in employee-details.component.html*/
  isRemoving: boolean;
/** Flag for letting know the user that inserting is in progress */
  /* Used in employee-details.component.html*/
  isInserting: boolean;

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  name = new FormControl('', [Validators.required]);
  typeForm = new FormControl();
  userTypes: Array<string> = ["ADMIN", "REGULAR"];
  userTypeString: string;
  telephone = new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('[0-9]+')]);
  address = new FormControl('', [Validators.required]);
  hiring_date = new FormControl('', [Validators.required, Validators.pattern('[0-9][0-9]\-[0-9][0-9]\-[0-9][0-9][0-9][0-9]')]);

  //for reports
  reportType = new FormControl();
  reportTypes: Array<string> = ["PDF", "CSV"];
  reportTypeString: string;
  fromDate = new FormControl('', [Validators.required, Validators.pattern('[0-9][0-9][0-9][0-9]\-[0-9][0-9]\-[0-9][0-9]')]);
  toDate = new FormControl('', [Validators.required, Validators.pattern('[0-9][0-9][0-9][0-9]\-[0-9][0-9]\-[0-9][0-9]')]);

  constructor(public employeeModel: EmployeeModel, private apiService: ApiService, private toastr: ToastrService) {}




  /**
   * This is an Angular method
   * (one of the lifcecycle component hooks: https://angular.io/guide/lifecycle-hooks)
   * Will detect changes on the @Input data, e.g when user select a diferent employee from the list
   */
  ngOnChanges(changes: { employeeId: SimpleChange, addEmployee:SimpleChange }) {
    const { employeeId, addEmployee } = changes;

    //console.log(addClient);
    console.log(this.reportTypes);

    if (!employeeId || !employeeId.currentValue) this.clearComponentData();

    if (employeeId && employeeId.currentValue && employeeId.currentValue !== employeeId.previousValue) {
      this.clearForm();
      /** If employeeId changes on selection, get the selected employee details */
      this.getEmployeeDetailsData(employeeId.currentValue);
    }
  }

  clearForm()
  {
    this.username.reset();
    this.password.reset();
    this.typeForm.reset();
    this.name.reset();
    this.telephone.reset();
    this.address.reset();
    this.hiring_date.reset();
  }

  clearFormForReport()
  {
    this.username.reset();
    this.reportType.reset();
    this.fromDate.reset();
    this.toDate.reset();
  }

  /**
   * Requesting employee details data through the API Service
   * @param employeeId  {string}
   */
  getEmployeeDetailsData(employeeId: string): void {
    this.apiService.getEmployeeDetails(employeeId).subscribe(
      (data: EmployeeInterface) => {
        /** Saving the obtained employee data into a variable */
        this.employeeData = data;
        /** Making copy of the initial employee data (for comparing purpose only) */
        this._employeeData = Object.assign({}, this.employeeData);
      },
      (error: HttpErrorResponse) => console.error(error)
    );
  }

  /**
   * Will submit the employee data if has been changed
   * This method is it bind to the `Save Employee` button
   * in employee-details.component.html
   */
  onEmployeeSave(): void {
    /** Initialize the isSaving flag */
    this.isSaving = true;
    /** Trigger the saving method from the API Service passing the employeeData*/
    this.apiService.saveEmployeeDetails(this.employeeData).subscribe(
      /** On Success */
      (data: EmployeeInterface) => {
        /** Update the copy of the initial employee data */
        this._employeeData = data;
        /** Notify the parent component to refresh the employee list */
        this.onSaveSuccess.emit(true);
        /** Notify the user with a successful message */
        this.toastr.success('Employee details updated!');
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
   * Will remove the Employee data through the API service
   * This method is it bind to the `Delete` button
   */
  onEmployeeRemove(): void {
    /** Initialize the isRemoving flag */
    this.isRemoving = true;
    /** Trigger the removing method from the API Service passing the employee username*/
    this.apiService.deleteEmployee(this.employeeData.username).subscribe(
      () => {
        /** End the isRemoving flag */
        this.isRemoving = false;
        /** Clear the component data */
        this.clearComponentData();
        /** Notify the parent component to refresh the employee list */
        this.onSaveSuccess.emit(true);
        /** Notify the user with a successful message */
        this.toastr.success('Employee was removed!');
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
   * Will determinate if the data has been changed or not
   * Buttons will remaing disabled if not change has happened
   * 
   * This is used in employee-details.component.html
   */
  hasEmployeeDataChanged(): boolean {
    return JSON.stringify(this.employeeData) !== JSON.stringify(this._employeeData);
  }

  /** This method will clear the `employeeData` value and `_employeeData` copy value */
  clearComponentData(): void {
    this.employeeData = undefined;
    this._employeeData = undefined;
  }

  /**
   * Will submit the employee data if has been changed
   * This method is it bind to the `Save New Employee` button
   * in employee-details.component.html
   */
  onNewEmployeeSave(): void {
    /** Initialize the isInserting flag */
    this.isInserting = true;
    this.employeeData={
      username : this.username.value,
      password : this.password.value,
      type : this.typeForm.value,
      name : this.name.value,
      telephone : this.telephone.value,
      address : this.address.value,
      hiring_date : this.hiring_date.value,
      activityList: new Array<ActivityInterface>()
    }

    /** Trigger the saving method from the API Service passing the employeeData*/
    this.apiService.insertEmployeeDetails(this.employeeData).subscribe(
      /** On Success */
      (data: EmployeeInterface) => {
        //console.log(this.clientData.ssn);
        /** Update the copy of the initial employee data */
        this._employeeData = data;
        /** Notify the parent component to refresh the employee list */
        this.onSaveSuccess.emit(true);
        /** Notify the user with a successful message */
        this.toastr.success('Employee inserted!');
        this.addEmployee = false;
        this.clearForm();
      },
      /** On Error */
      (error: HttpErrorResponse) => {
        /** Notify the user about the error */
        this.toastr.error(error.message);
        /** End the isSaving flag */
        this.isInserting = false;
        this.clearForm();
        this.employeeData = undefined;
      },
      /** End the isSaving flag */
      () => (this.isInserting = false)
    );
  }

  /**
   * Will generate a report
   * This method is it bind to the `Generate Report` button at bottom of form
   */


onGenerateReport() {
        
        
        this.apiService.getEmployeeDetails(this.username.value).subscribe(
            (data: EmployeeInterface) => {
                window.open(`http://localhost:8080/bank/admin/employee/getActivityListFor/${this.username.value}/${this.fromDate.value}/${this.toDate.value}/${this.reportTypeString}`,'_blank');
            },
            (error: HttpErrorResponse) => console.error(error)
        );
    }

  userTypeSelect(value: string) {
      this.userTypeString=value;
  }

  reportTypeSelect(value: string) {
    this.reportTypeString=value;
  }

  getTelephoneErrorMessage() {
    return this.telephone.hasError('required') ? 'You must enter a value' :
        this.telephone.hasError('pattern') ? 'Not a valid telephone number' :
        this.telephone.hasError('minlength') ? 'Telephone number must have 10 digits' :
        this.telephone.hasError('maxlengt') ? 'Telephone number must have 10 digits' :
            '';
  }

  getUsernameErrorMessage() {
    return this.username.hasError('required') ? 'You must enter a value' :
            '';
  }

  getPasswordErrorMessage() {
    return this.password.hasError('required') ? 'You must enter a value' :
            '';
  }

  getNameErrorMessage() {
    return this.name.hasError('required') ? 'You must enter a value' :
            '';
  }

  getHiringDateErrorMessage() {
    return this.hiring_date.hasError('required') ? 'You must enter a value' :
        this.hiring_date.hasError('pattern') ? 'Hiring date must be formatted like: dd-mm-yyyy' :
            '';
  }

  getAddressErrorMessage() {
    return this.address.hasError('required') ? 'You must enter a value' :
            '';
  }
  
  getFromDateErrorMessage() {
    return this.fromDate.hasError('required') ? 'You must enter a value' :
        this.fromDate.hasError('pattern') ? 'From date must be formatted like: yyyy-mm-dd' :
            '';
  }

  getToDateErrorMessage() {
    return this.toDate.hasError('required') ? 'You must enter a value' :
        this.toDate.hasError('pattern') ? 'To date must be formatted like: yyyy-mm-dd' :
            '';
  }
  
  
}
