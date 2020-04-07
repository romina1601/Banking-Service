package utcn.labs.sd.bankingservice.domain.service;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.labs.sd.bankingservice.domain.data.converter.AccountConverter;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;
import utcn.labs.sd.bankingservice.domain.data.repository.AccountRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ActivityRepository;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO2;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static utcn.labs.sd.bankingservice.domain.service.ServiceInterface.getUser;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public AccountService(AccountRepository accountRepository, ActivityRepository activityRepository) {
        this.accountRepository = accountRepository;
        this.activityRepository = activityRepository;
    }

    public List<AccountDTO> getAllAccounts() {

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " retrieved all the accounts";
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());

        Activity a = new Activity(user, "getAllAccounts", currentTimestamp.toString(),
                description);

        activityRepository.save(a);


        return AccountConverter.toDto(accountRepository.findAll());
    }

    public AccountDTO getAccountById(Integer accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) throw new NotFoundException("No account found with that accountId");

        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " retrieved information about account " +
                                account.getAccountId();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        Activity a = new Activity(user, "getAccountById", currentTimestamp.toString(),
                    description);
        activityRepository.save(a);


        return AccountConverter.toDto(account);
    }

    public AccountDTO createAccount(AccountDTO2 accountDto2) throws Exception {
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        if (accountDto2.getBalance() < 0) {
            throw new Exception("Impossible to have a negative balance");
        }
        Account account = new Account(accountDto2.getAccountType(), currentTimestamp.toString(), accountDto2.getBalance());
        Account newAccount = accountRepository.save(account);


        //inserting activity
        String user = getUser();
        String description = "The employee having username: " + user + " created the account " +
                             newAccount.getAccountId();
        Activity a = new Activity(user, "createAccount", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return AccountConverter.toDto(newAccount);
    }


    public AccountDTO updateAccount(Integer accountId, AccountDTO accountDto) throws Exception {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new NotFoundException("No account found with that id");
        }
        if (accountDto.getBalance() != 0) {
            account.setBalance(accountDto.getBalance());
        }
        if (accountDto.getAccountType() != null) {
            account.setAccountType(accountDto.getAccountType());
        }

        //inserting activity
        String user = getUser();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        String description = "The employee having username: " + user + " updated the account " +
                             account.getAccountId();
        Activity a = new Activity(user, "updateAccount", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        return AccountConverter.toDto(accountRepository.save(account));
    }


    public void deleteAccount(Integer accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null)
        {
            throw new NotFoundException("No account with that accountId");
        }

        //inserting activity
        String user = getUser();
        Timestamp currentTimestamp = new Timestamp(Instant.now().toEpochMilli());
        String description = "The employee having username: " + user + " deleted the account " +
                             account.getAccountId();
        Activity a = new Activity(user, "deleteAccount", currentTimestamp.toString(),
                     description);
        activityRepository.save(a);

        accountRepository.delete(account);
    }


}
