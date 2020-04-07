import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { EmployeeModel } from 'src/app/shared/models/employee.model';
import { ApiService } from 'src/app/shared/services/api.service';
import { EmployeeInterface } from 'src/app/shared/models/interfaces/employee';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {
  /** Flag for letting know the user that something is loading/happening */
  isProcessing: boolean;

  //flag to see if add employee button is selected
  addEmployee: boolean = false;

  //flag to see if generate report button is selected
  generateReport: boolean = false;


  constructor(public employeeModel: EmployeeModel, private apiService: ApiService) {}

  ngOnInit() {
    this.getAllEmployees();
  }

  getAllEmployees(reloadEmployees: boolean = false) {
    if (!reloadEmployees) {
      /** Initialize the loading process */
      this.isProcessing = true;
      /** Clear the Employee Model values (if it was instantiated before) */
      this.employeeModel.clear();
    }
    this.apiService.getEmployees().subscribe(
      /** On Success: save the list of employees in the employee model */
      (data: Array<EmployeeInterface>) => (this.employeeModel.all = data),
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

  onAddEmployee()
  {
    this.addEmployee = true;
    this.generateReport = false;
    //console.log("button pressed");
   // this.addNewClient.emit(this.addClient);
  }

  onGenerateReport()
  {
    this.generateReport = true;
    this.addEmployee = false;
    //console.log("button pressed");
   // this.addNewClient.emit(this.addClient);
  }

  onEmployeeIdSelected(employeeId: string) {
    this.employeeModel.selectedEmployeeId = employeeId;
    this.addEmployee = false;
    this.generateReport = false;
  }
}
