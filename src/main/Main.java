package src.main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import src.main.model.Bank;
import src.main.model.Transaction;
import src.main.model.account.Account;

public class Main {

   static String ACCOUNTS_FILE = "src/main/data/accounts.txt";            
   static String TRANSACTIONS_FILE = "src/main/data/transactions.txt";

   static Bank bank = new Bank();

    public static void main(String[] args) {
    
        try {
            ArrayList<Account> accounts = returnAccounts();
            loadAccounts(accounts);

            ArrayList<Transaction> transactions = returnTransactions();
            runTransactions(transactions);
            bank.deductTaxes();
            for (Account account : accounts) {
                System.out.println("\n\t\t\t\t\t ACCOUNT\n\n\t"+account+"\n\n");
                transactionHistory(account.getId());
            }
            
         } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Account createObject(String[] values) throws Exception{
        
        return (Account) Class.forName("src.main.model.account." + values[0])
          .getConstructor(String.class, String.class, double.class)
          .newInstance(values[1], values[2], (Double.parseDouble(values[3])));
        
    }

     public static void wait(int milliseconds) {
         try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
         } catch (InterruptedException e) {
             System.out.println(e.getMessage());
         }
     }

     public static ArrayList<Account> returnAccounts() throws Exception{
        ArrayList<Account> accList = new ArrayList<Account>();
        String fileString = Files.readString((Paths.get(ACCOUNTS_FILE)));
        String[] lines = fileString.split("\n");
        
        for (String line : lines){
            accList.add(createObject(line.split(",")));
        }
        return accList;
     }

     public static void loadAccounts(ArrayList<Account> accounts) {
        for (Account acc : accounts) bank.addAccount(acc);
     }

     public static ArrayList<Transaction> returnTransactions() throws Exception{
        ArrayList<Transaction> transList = new ArrayList<Transaction>();
        String fileString = Files.readString((Paths.get(TRANSACTIONS_FILE)));
        String[] lines = fileString.split("\n");
        
        for (String l : lines){
            transList.add(new Transaction(
                Transaction.Type.valueOf((l.split(",")[1])),
                Long.parseLong(l.split(",")[0]),
                l.split(",")[2],
                Double.parseDouble(l.split(",")[3])));
        }
        return transList;
     }

     public static void runTransactions(ArrayList<Transaction> transList) {
        for (Transaction transaction :transList){
            bank.executeTransaction(transaction);
        }
     }

     public static void transactionHistory(String id) {
        System.out.println("\t\t\t\t   TRANSACTION HISTORY\n\t");
        for (Transaction trans : bank.getTransactions(id)) {
            System.out.println("\t"+trans+"\n");
            wait(300);
        }
        System.out.println("\n\t\t\t\t\tAFTER TAX\n");
        System.out.println("\t" + bank.getAccount(id) +"\n\n\n\n");
     }
}
