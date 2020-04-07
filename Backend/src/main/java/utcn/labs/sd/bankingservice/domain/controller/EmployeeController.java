package utcn.labs.sd.bankingservice.domain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utcn.labs.sd.bankingservice.core.configuration.SwaggerTags;
import utcn.labs.sd.bankingservice.domain.dto.ActivityDTO;
import utcn.labs.sd.bankingservice.domain.dto.EmployeeDTO;
import utcn.labs.sd.bankingservice.domain.service.EmployeeService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Api(tags = {SwaggerTags.BANKING_SERVICE_TAG})
@RestController
@RequestMapping("/bank/admin/employee")
@CrossOrigin
class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @ApiOperation(value = "getAllEmployees", tags = SwaggerTags.EMPLOYEE_TAG)
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {return employeeService.getAllEmployees();
    }

    @ApiOperation(value = "findEmployeeById", tags = SwaggerTags.EMPLOYEE_TAG)
    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<?> findEmployeeById(@PathVariable("employeeId") String employeeId) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "insertEmployee", tags = SwaggerTags.EMPLOYEE_TAG)
    @PostMapping
    public ResponseEntity<?> insertEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employeeDtoToBeInserted;
        try {
            employeeDtoToBeInserted = employeeService.createEmployee(employeeDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeDtoToBeInserted, HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateEmployee", tags = SwaggerTags.EMPLOYEE_TAG)
    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable("employeeId") String employeeId,
                                            @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.updateEmployee(employeeId, employeeDTO);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "deleteEmployee", tags = SwaggerTags.EMPLOYEE_TAG)
    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employeeId") String employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (NotFoundException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "getActivityForEmployee", tags = SwaggerTags.EMPLOYEE_TAG)
    @GetMapping(value = "/getActivityListFor/{employeeId}/{from}/{to}/{type}")
    public ResponseEntity<?> getActivityList(@PathVariable("employeeId") String employeeId,
                                             @PathVariable("from") String from,
                                             @PathVariable("to") String to,
                                             @PathVariable("type") String type)
    {
        try {
            employeeService.getActivityList(employeeId, from, to, type);

            String pathName="";
            String parseMediaType="";

            if(type.equalsIgnoreCase("PDF"))
            {
                pathName = "output.pdf";
                parseMediaType = "application/octet-stream";
            }
            else if (type.equalsIgnoreCase("CSV"))
            {
                pathName = "output.csv";
                parseMediaType = "application/octet-stream";
            }


            byte[] contents = Files.readAllBytes(new File(pathName).toPath());
            Files.delete(new File(pathName).toPath());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(parseMediaType));
            httpHeaders.setContentDispositionFormData(pathName, pathName);
            httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(contents, httpHeaders, HttpStatus.OK);
            //return new ResponseEntity<>(activityDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
