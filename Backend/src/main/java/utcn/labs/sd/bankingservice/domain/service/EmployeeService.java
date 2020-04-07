package utcn.labs.sd.bankingservice.domain.service;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.labs.sd.bankingservice.domain.data.converter.ActivityConverter;
import utcn.labs.sd.bankingservice.domain.data.converter.EmployeeConverter;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;
import utcn.labs.sd.bankingservice.domain.data.entity.Employee;
import utcn.labs.sd.bankingservice.domain.data.repository.EmployeeRepository;
import utcn.labs.sd.bankingservice.domain.dto.ActivityDTO;
import utcn.labs.sd.bankingservice.domain.dto.EmployeeDTO;
import utcn.labs.sd.bankingservice.domain.exception.CreateEmployeeException;
import utcn.labs.sd.bankingservice.domain.factory.Report;
import utcn.labs.sd.bankingservice.domain.factory.ReportFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDTO> getAllEmployees()
    {
        return EmployeeConverter.toDto(employeeRepository.findAll());
    }

    public EmployeeDTO getEmployeeById(String employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) throw new NotFoundException("No employee found with that employeeId");
        return EmployeeConverter.toDto(employee);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDto) throws Exception {
        Employee employee = new Employee(employeeDto.getUsername(), employeeDto.getPassword(),
                employeeDto.getType(), employeeDto.getName(), employeeDto.getTelephone(),
                employeeDto.getAddress(), employeeDto.getHiring_date(), null);
        Employee possibleAlreadyExistingEmployee = employeeRepository.findById(employeeDto.getUsername()).orElse(null);
        if (possibleAlreadyExistingEmployee == null) {
            Employee newEmployee = employeeRepository.save(employee);
            return EmployeeConverter.toDto(newEmployee);
        } else {
            throw new CreateEmployeeException("Username already taken! Please choose another username.");
        }
    }

    public EmployeeDTO updateEmployee(String employeeId, EmployeeDTO employeeDto) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new CreateEmployeeException("No employee found with that employeeId");
        }

        employee.setName(employeeDto.getName());
        employee.setTelephone(employeeDto.getTelephone());
        employee.setAddress(employeeDto.getAddress());
        employee.setHiring_date(employeeDto.getHiring_date());

        return EmployeeConverter.toDto(employeeRepository.save(employee));
    }

    public void deleteEmployee(String employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new CreateEmployeeException("No employee found with that employeeId");
        }

        employeeRepository.delete(employee);
    }

    public void getActivityList(String employeeId, String from, String to, String reportType) throws Exception
    {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new CreateEmployeeException("No employee found with that employeeId");
        }

        List<Activity>activityList = employee.getActivityList();


        ReportFactory reportFactory = new ReportFactory();
        Report reportPdf = reportFactory.getreportType(reportType);
        reportPdf.generateReport(activityList, from, to);

        //Report reportCsv = reportFactory.getreportType("CSV");
        //reportCsv.generateReport(activityList, from, to);
    }


}
