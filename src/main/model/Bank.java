package src.main.model;

import java.util.ArrayList;

import src.main.model.account.Account;

public class Bank {
    
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;


    public Bank() {
        this.accounts = new ArrayList<Account>(1);
        this.transactions = new ArrayList<Transaction>(1);
    }

    public void addAccount(Account account) {
        this.accounts.add(account.clone());
    }

    
}
