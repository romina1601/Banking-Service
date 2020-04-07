package utcn.labs.sd.bankingservice.domain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utcn.labs.sd.bankingservice.core.configuration.SwaggerTags;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO2;
import utcn.labs.sd.bankingservice.domain.service.AccountService;

import java.util.List;

@Api(tags = {SwaggerTags.BANKING_SERVICE_TAG})
@RestController
@RequestMapping("/bank/employee/account")
@CrossOrigin
class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "getAllAccounts", tags = SwaggerTags.ACCOUNT_TAG)
    @RequestMapping(method = RequestMethod.GET)
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @ApiOperation(value = "findAccountById", tags = SwaggerTags.ACCOUNT_TAG)
    @GetMapping(value = "/{accountId}")
    public ResponseEntity<?> findAccountById(@PathVariable("accountId") Integer accountId) {
        try {
            AccountDTO accountDto = accountService.getAccountById(accountId);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "insertAccount", tags = SwaggerTags.ACCOUNT_TAG)
    @PostMapping
    public ResponseEntity<?> insertAccount(@RequestBody AccountDTO2 accountDto2) {
        AccountDTO accountDtoToBeInserted;
        try {
            accountDtoToBeInserted = accountService.createAccount(accountDto2);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accountDtoToBeInserted, HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateAccount", tags = SwaggerTags.ACCOUNT_TAG)
    @PutMapping(value = "/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable("accountId") Integer accountId, @RequestBody AccountDTO accountDto) {
        try {
            AccountDTO updatedAccount = accountService.updateAccount(accountId, accountDto);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteAccount", tags = SwaggerTags.ACCOUNT_TAG)
    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") Integer accountId) {
        try {
            accountService.deleteAccount(accountId);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (NotFoundException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
