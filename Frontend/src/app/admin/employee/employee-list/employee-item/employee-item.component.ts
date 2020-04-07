import { Component, Input, Output, EventEmitter } from '@angular/core';
import { EmployeeModel } from 'src/app/shared/models/employee.model';
import { EmployeeInterface } from 'src/app/shared/models/interfaces/employee';

@Component({
  selector: 'app-employee-item',
  templateUrl: './employee-item.component.html',
  styleUrls: ['./employee-item.component.scss']
})
export class EmployeeItemComponent {
  /** Index number */
  @Input() index: number;

  /** Will hold the data of a single Client */
  @Input() employeeData: EmployeeInterface;

  @Output()
  selectedEmployeeId: EventEmitter<string> = new EventEmitter();

  constructor(public employeeModel: EmployeeModel) {}

  /**
   * Will notify the parent component (employee-list.component.ts)
   * when an employee as been selected, an pass the ssn value of the selected employee
   * 
   * It first goes to employee.module.ts and 
   * from there employee-list.component.ts gets this value
   * Use the exact same name for the variables (this is how they are linked)
   */
  onEmployeeSelect() {
    this.selectedEmployeeId.emit(this.employeeData.username);
  }
}
