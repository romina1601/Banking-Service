package utcn.labs.sd.bankingservice.domain.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import utcn.labs.sd.bankingservice.domain.data.entity.Account;
import utcn.labs.sd.bankingservice.domain.data.entity.Client;
import utcn.labs.sd.bankingservice.domain.data.entity.enums.AccountType;
import utcn.labs.sd.bankingservice.domain.data.repository.AccountRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ActivityRepository;
import utcn.labs.sd.bankingservice.domain.data.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientServiceTest {

    @Mock
    private ClientRepository mockClientRepository;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private ActivityRepository mockActivityRepository;

    private ClientService clientService;
    private Client client1;
    private Client client2;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        clientService = new ClientService(mockClientRepository, mockAccountRepository, mockActivityRepository);

        account1 = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 500.0f);
        List<Account> accountList1 = new ArrayList<>();
        accountList1.add(account1);

        account2 = new Account(4, AccountType.CREDIT,  "2019-02-24 16:18:25.861", 320.0f);
        List<Account> accountList2 = new ArrayList<>();
        accountList2.add(account2);

        client1 = new Client("2980116323911", "Rachel", "Green",
                "1563224856", "Str.Central Perk", "rachelg@gmail.com", accountList1);
        client2 = new Client("1980116323911", "Ross", "Geller",
                "15634562348", "Yemen Street, Yemen", "drrossg@gmail.com", accountList2);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        Mockito.when(mockClientRepository.findById("2980116323911"))
                .thenReturn(java.util.Optional.ofNullable(client1));
        Mockito.when(mockAccountRepository.findById(3))
                .thenReturn(java.util.Optional.ofNullable(account1));
        Mockito.when(mockClientRepository.save(any()))
                .thenReturn(account1);



    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void payBill() {
        Account account = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 400.0f);
        List<Account> accountListTest = new ArrayList<>();
        accountListTest.add(account);
        Client client = new Client("2980116323911", "Rachel", "Green",
                "1563224856", "Str.Central Perk",
                "rachelg@gmail.com", accountListTest);

        try
        {
            Assert.assertEquals(client.getAccountList().get(0).getBalance(),
                    clientService.payBill("2980116323911",
                    "detail", 100.0f, 3).getAccountList().get(0).getBalance(), 0.1);
        }
        catch (Exception e)
        { }

    }

    @Test
    public void transferMoney() {
        Account account = new Account(3, AccountType.SAVINGS,  "2019-03-20 16:18:25.861", 400.0f);
        List<Account> accountListTest = new ArrayList<>();
        accountListTest.add(account);
        Client client = new Client("2980116323911", "Rachel", "Green",
                "1563224856", "Str.Central Perk",
                "rachelg@gmail.com", accountListTest);

        try
        {
            Assert.assertEquals(client.getAccountList().get(0).getBalance(),
                    clientService.transferMoney("2980116323911", 3, 4, 100.0f).
                    getAccountList().get(0).getBalance(), 0.1);
        }
        catch (Exception e)
        { }
    }
}