package utcn.labs.sd.bankingservice.domain.data.entity;

import utcn.labs.sd.bankingservice.domain.data.entity.enums.AccountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "account_table")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "client_ssn")
    private String clientSsn;


    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "date_of_creation")
    @NotNull
    private String creationDate;

    @Column(name = "balance")
    @Positive
    private float balance;



    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getClientSsn() {
        return clientSsn;
    }

    public void setClientSsn(String clientSsn) {
        this.clientSsn = clientSsn;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Account(int accountId, String clientSsn, AccountType accountType, String creationDate, float balance) {
        this.accountId = accountId;
        this.clientSsn = clientSsn;
        this.accountType = accountType;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Account(AccountType accountType, String creationDate, float balance) {
        this.accountType = accountType;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Account() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("accountId=").append(accountId);
        sb.append(", accountType=").append(accountType);
        sb.append(", creationDate='").append(creationDate).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}
