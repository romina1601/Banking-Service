package utcn.labs.sd.bankingservice.domain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utcn.labs.sd.bankingservice.core.configuration.SwaggerTags;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO;
import utcn.labs.sd.bankingservice.domain.dto.ClientDTO;
import utcn.labs.sd.bankingservice.domain.service.ClientService;

import java.util.List;

@Api(tags = {SwaggerTags.BANKING_SERVICE_TAG})
@RestController
@RequestMapping("/bank/employee/client")
@CrossOrigin
class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "getAllClients", tags = SwaggerTags.CLIENT_TAG)
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }


    @ApiOperation(value = "findClientById", tags = SwaggerTags.CLIENT_TAG)
    @GetMapping(value = "/{clientId}")
    public ResponseEntity<?> findClientById(@PathVariable("clientId") String clientId) {
        try {
            ClientDTO clientDto = clientService.getClientById(clientId);
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "insertClient", tags = SwaggerTags.CLIENT_TAG)
    @PostMapping
    public ResponseEntity<?> insertClient(@RequestBody ClientDTO clientDto) {
        ClientDTO clientDtoToBeInserted;
        try {
            clientDtoToBeInserted = clientService.createClient(clientDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(clientDtoToBeInserted, HttpStatus.CREATED);
    }

    @ApiOperation(value = "updateClient", tags = SwaggerTags.CLIENT_TAG)
    @PutMapping(value = "/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable("clientId") String clientId, @RequestBody ClientDTO clientDto) {
        try {
            ClientDTO changedClient = clientService.changeClient(clientId, clientDto);
            return new ResponseEntity<>(changedClient, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteClient", tags = SwaggerTags.CLIENT_TAG)
    @DeleteMapping(value = "/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable("clientId") String clientId) {
        try {
            /*ClientDTO clientDto = clientService.getClientById(clientId);
            for (Account account : clientDto.getAccountList()) {
                clientDto.getAccountList().remove(account);
            }*/
            clientService.deleteClient(clientId);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (NotFoundException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*@ApiOperation(value = "addAccountToClient", tags = SwaggerTags.CLIENT_TAG)
    @PostMapping(value = "/account/{clientId}")
    public ResponseEntity<?> addAccountToClient(@PathVariable("clientId") String clientId, @RequestBody AccountDTO accountDto) {
        try {
            /*clientService.addAccountToClient(clientId, accountDto);
            ClientDTO clientDto = clientService.getClientById(clientId);*/
            /*ClientDTO clientDto = clientService.addAccountToClient(clientId, accountDto);
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    @ApiOperation(value = "addAccountToClient", tags = SwaggerTags.CLIENT_TAG)
    @PostMapping(value = "/account/{clientId}/{accountId}")
    public ResponseEntity<?> addAccountToClient(@PathVariable("clientId") String clientId, @PathVariable int accountId) {
        try {
            ClientDTO clientDto = clientService.addAccountToClient(clientId, accountId);
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "deleteAccountFromClient", tags = SwaggerTags.CLIENT_TAG)
    @DeleteMapping(value = "/account/{clientId}/{accountId}")
    public ResponseEntity<?> deleteAccountFromClient(@PathVariable("clientId") String clientId, @PathVariable("accountId") int accountId) {
        try {
            clientService.deleteAccountFromClient(clientId, accountId);
            return new ResponseEntity<>(clientService.getClientById(clientId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "payBill", tags = SwaggerTags.CLIENT_TAG)
    @PatchMapping(value = "/{clientId}/{billDetails}/{amount}/{accountId}")
    public ResponseEntity<?> payBillFromClient(@PathVariable("clientId") String clientId, @PathVariable("billDetails") String billDetails,
                                     @PathVariable("amount") float amount, @PathVariable("accountId") int accountId) {
        try
        {
            clientService.payBill(clientId, billDetails, amount, accountId);
            //return new ResponseEntity<>(clientService.getClientById(clientId), HttpStatus.OK);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (NotFoundException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "transferMoney", tags = SwaggerTags.CLIENT_TAG)
    @PatchMapping(value = "transfer/{clientId}/{sourceAccountId}/{destinationAccountId}/{amount}")
    public ResponseEntity<?> transferMoneyFromClient(@PathVariable("clientId") String clientId,
                                                     @PathVariable("sourceAccountId") int accountId1,
                                                     @PathVariable("destinationAccountId") int accountId2,
                                                     @PathVariable("amount") float amount) {
        try
        {
            clientService.transferMoney(clientId, accountId1, accountId2, amount);
            //return new ResponseEntity<>(c, HttpStatus.OK);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (NotFoundException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
