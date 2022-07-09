package src.main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.impl.Taxable;

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

    private void addTransaction(Transaction transaction) {
        this.transactions.add(new Transaction(transaction));
    }

    public Transaction[] getTransactions(String accountId) {
        List<Transaction> list = this.transactions.stream()
        .filter((transaction) -> transaction.getId().equals(accountId))
        .collect(Collectors.toList());

        return list.toArray(new Transaction[list.size()]);
    }

    public Account getAccount(String transactionId) {
        Account acc = this.accounts.stream()
        .filter((account) -> account.getId().equals(transactionId))
        .findFirst()
        .orElse(null);
        
        return acc;
    }

    private boolean withdrawTransaction(Transaction transaction) {
        if (getAccount(transaction.getId()).withdraw(transaction.getAmount())){
            addTransaction(transaction);
            return true;
        }else{
            return false;
        }
    }

    private boolean depositTransaction(Transaction transaction) {
        if (getAccount(transaction.getId()).deposit(transaction.getAmount())){
            addTransaction(transaction);
            return true;
        }else{
            return false;
        }
    }

    public void executeTransaction(Transaction transaction) {
        switch (transaction.getType()){
            case WITHDRAW: withdrawTransaction(transaction); break;
            case DEPOSIT: depositTransaction(transaction); break;
        }
    }

    private double getIncome(Account account) {

        Transaction[] transactions = getTransactions(account.getId());
        double income = 0;

        for (Transaction transaction : transactions){
            switch (transaction.getType()) {
                case WITHDRAW:
                    income -= transaction.getAmount();
                    break;

                case DEPOSIT:
                    income += transaction.getAmount();
                default:
                continue;
            }
        }
        return income;
    }

    public void deductTaxes() {
        for (Account account : accounts){
            if (getIncome(account) > 3000 
            && Taxable.class.isAssignableFrom(account.getClass())) {
                Taxable taxable = (Taxable)account;
                taxable.tax(getIncome(account));
            }
        }
    }

}
