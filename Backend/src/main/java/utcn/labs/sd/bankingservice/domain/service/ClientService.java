package utcn.labs.sd.bankingservice.domain.service;

import javassist.NotFoundException;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.labs.sd.bankingservice.domain.data.converter.ClientConverter;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;
import utcn.labs.sd.bankingservice.domain.data.entity.Client;
import utcn.labs.sd.bankingservice.domain.data.repository.AccountRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ActivityRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ClientRepository;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO;
import utcn.labs.sd.bankingservice.domain.dto.ClientDTO;
import utcn.labs.sd.bankingservice.domain.exception.CreateClientException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static utcn.labs.sd.bankingservice.domain.service.ServiceInterface.getUser;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository,
                         ActivityRepository activityRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.activityRepository = activityRepository;
    }

    public List<ClientDTO> getAllClients() {
        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " retrieved all the clients";
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "getAllClients", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        System.out.println(a.toStringForCSV());

        return ClientConverter.toDto(clientRepository.findAll());
    }

    public ClientDTO getClientById(String clientId) throws Exception {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) throw new NotFoundException("No client found with that clientId");

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " retrieved information" +
                             " about client " + clientId;
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "getClientById", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return ClientConverter.toDto(client);
    }

    public ClientDTO createClient(ClientDTO clientDto) throws Exception {
        Client client = new Client(clientDto.getSsn(), clientDto.getFirstname(), clientDto.getLastname(),
                clientDto.getIdentityCardNumber(), clientDto.getAddress(), clientDto.getEmail(),
                null);
        Client possibleAlreadyExistingClient = clientRepository.findById(clientDto.getSsn()).orElse(null);
        if (possibleAlreadyExistingClient == null)
        {
            if (clientDto.getFirstname() != null) {
                client.setFirstname(clientDto.getFirstname());
            }

            if (clientDto.getLastname() != null) {
                client.setLastname(clientDto.getLastname());
            }

            if (clientDto.getIdentityCardNumber() != null) {
                client.setIdentityCardNumber(clientDto.getIdentityCardNumber());
            }

            if (clientDto.getAddress() != null) {
                client.setAddress(clientDto.getAddress());
            }
            if (clientDto.getEmail() != null) {
                client.setEmail(clientDto.getEmail());
            }

            //inserting activity
            String user = getUser();
            String description = "The employee having username: " + user + " inserted a new client: " +
                                 client.getSsn();
            Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
            Activity a = new Activity(user, "insertClient", currentTimestamp.toString(),
                         description);
            activityRepository.save(a);

            return ClientConverter.toDto(clientRepository.save(client));
        }
        else
        {
            throw new CreateClientException("Client already exists!");
        }
    }

    public ClientDTO changeClient(String clientId, ClientDTO clientDto) throws Exception {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new NotFoundException("No client found with that clientId");
        }
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setIdentityCardNumber(clientId);
        client.setAddress(clientDto.getAddress());
        client.setEmail(clientDto.getEmail());

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " updated information on client " +
                             client.getSsn();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "updateClient", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return ClientConverter.toDto(clientRepository.save(client));
    }

    public void deleteClient(String clientId) throws Exception {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new NotFoundException("No client with that clientId");
        }
        List<Account> clientListOfAccounts = client.getAccountList();
        for (Account account : clientListOfAccounts) {
            accountRepository.delete(account);
        }

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " deleted client " +
                             client.getSsn();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "deleteClient", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        clientRepository.delete(client);
    }

    public ClientDTO addAccountToClient (String clientId, int accountId) throws Exception
    {
        Client client = clientRepository.findById(clientId).orElse(null);
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Account account = accountRepository.findById(accountId).orElse(null);
        client.getAccountList().add(account);
        account.setClientSsn(clientId);
        clientRepository.save(client);
        accountRepository.save(account);

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " added account " +
                             account.getAccountId() +" to client " + client.getSsn();
        Activity a = new Activity(user, "addAccountToClient", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return ClientConverter.toDto(client);
    }

    public ClientDTO deleteAccountFromClient(String clientId, Integer accountId)
    {
        Client client = clientRepository.findById(clientId).orElse(null);
        accountRepository.deleteById(accountId);

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " deleted account " +
                             accountId + "from client " + client.getSsn();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "deleteAccountFromClient", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return ClientConverter.toDto(client);
    }

    public void payBill(String clientId, String billDetails, float amount, Integer accountId) throws Exception
    {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new NotFoundException("No client with that clientId");
        }
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new NotFoundException("No account found with that id");
        }
        List<Account> clientListOfAccounts = client.getAccountList();
        if (!clientListOfAccounts.contains(account))
        {
            throw new NotFoundException("Client does not have this account");
        }
        if(amount < 0)
        {
            throw new Exception("Cannot transfer negative sum of money");
        }
        if(account.getBalance() < amount)
        {
            throw new Exception("Insufficient funds");
        }
        else
        {
            account.setBalance(account.getBalance()-amount);
        }
        accountRepository.save(account);

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " payed the bill: " +
                             billDetails + " from account " + account.getAccountId() + " of client "
                             + client.getSsn();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "payBill", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        //return ClientConverter.toDto(client);

    }

    public void transferMoney(String clientId, int accountId1, int accountId2, float amount) throws Exception
    {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new NotFoundException("No client with that clientId");
        }
        Account account1 = accountRepository.findById(accountId1).orElse(null);
        if (account1 == null) {
            throw new NotFoundException("No account found with that id");
        }
        Account account2 = accountRepository.findById(accountId2).orElse(null);
        if (account2 == null) {
            throw new NotFoundException("No account found with that id");
        }
        List<Account> clientListOfAccounts = client.getAccountList();
        if (!clientListOfAccounts.contains(account1))
        {
            throw new NotFoundException("Source account does not exist");
        }
        if(account1.getBalance() < amount)
        {
            throw new Exception("Insufficient funds");
        }
        else
        {
            account1.setBalance(account1.getBalance()-amount);
            account2.setBalance((account2.getBalance()+amount));
        }

        accountRepository.save(account1);
        accountRepository.save(account2);

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " transferred the amount of " +
                             amount + " from account " + account1.getAccountId() + " of client " +
                             client.getSsn() + " into acount " + account2.getAccountId();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "transferMoney", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        //return ClientConverter.toDto(client);
    }
}
