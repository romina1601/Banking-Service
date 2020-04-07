package utcn.labs.sd.bankingservice.domain.service;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import utcn.labs.sd.bankingservice.domain.data.converter.AccountConverter;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.data.entity.enums.AccountType;
import utcn.labs.sd.bankingservice.domain.data.repository.AccountRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ActivityRepository;
import utcn.labs.sd.bankingservice.domain.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private ActivityRepository mockActivityRepository;

    private AccountService accountService;
    private Account account;

    @org.junit.Before
    public void setUp() {
        initMocks(this);

        accountService = new AccountService(mockAccountRepository, mockActivityRepository);

        account = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 500.0f);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        Mockito.when(mockActivityRepository.save(any()))
                .thenReturn(null);
        Mockito.when(mockAccountRepository.findAll())
                .thenReturn(accountList);
        Mockito.when(mockAccountRepository.findById(any()))
                .thenReturn(java.util.Optional.ofNullable(account));
        Mockito.when(mockAccountRepository.save(any())).
                thenReturn(account);

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void getAllAccounts()
    {
        Assert.assertEquals(1, accountService.getAllAccounts().size());
    }

    @org.junit.Test
    public void getAccountById() {
        Account acc = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 500.0f);

        try
        {
            Assert.assertEquals(acc.getAccountId(), accountService.getAccountById(3).getAccountId());
            Assert.assertEquals(acc.getBalance(), accountService.getAccountById(3).getBalance(), 0.1);
            Assert.assertEquals(acc.getAccountType(), accountService.getAccountById(3).getAccountType());
            Assert.assertEquals(acc.getCreationDate(), accountService.getAccountById(3).getCreationDate());
        }
        catch (Exception e)
        { }

    }

    @org.junit.Test
    public void createAccount() {
        Account acc = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 500.0f);
        AccountDTO accDTO = AccountConverter.toDto(acc);
        try
        {
            Assert.assertEquals(acc.getAccountId(), accountService.createAccount(accDTO).getAccountId());
            Assert.assertEquals(acc.getBalance(), accountService.createAccount(accDTO).getBalance(), 0.1);
            Assert.assertEquals(acc.getCreationDate(), accountService.createAccount(accDTO).getCreationDate());
            Assert.assertEquals(acc.getAccountType(), accountService.createAccount(accDTO).getAccountType());
        }
        catch (Exception e)
        { }
    }

    @org.junit.Test
    public void updateAccount() {
        Account acc = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 600.0f);
        Account sourceAcc = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 600.0f);
        AccountDTO accDTO = AccountConverter.toDto(acc);

        try
        {
            Assert.assertEquals(sourceAcc.getAccountId(), accountService.updateAccount(3, accDTO).getAccountId());
            Assert.assertEquals(sourceAcc.getBalance(), accountService.updateAccount(3, accDTO).getBalance(), 0.1);
            Assert.assertEquals(acc.getCreationDate(), accountService.updateAccount(3,accDTO).getCreationDate());
            Assert.assertEquals(acc.getAccountType(), accountService.updateAccount(3, accDTO).getAccountType());
        }
        catch (Exception e)
        { }

    }
}