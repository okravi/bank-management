package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;

import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;
import src.main.model.account.Account;
import src.main.model.account.impl.Taxable;


public class AccountTests {

    Account[] accounts;

    @Before
    public void setup() {
        accounts = new Account[] {
            new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
            new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60),
            new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", -100)
        };
    }

    @Test
    public void withdrawalTest(){
        accounts[0].withdraw(1440);
        assertEquals(84.51, accounts[0].getBalance());
    }

    @Test
    public void overdraftTest(){
        accounts[0].withdraw(1534.43);
        assertEquals(-15.42, accounts[0].getBalance());
    }

    @Test
    public void overdraftLimitTest(){
        accounts[0].withdraw(1726);
        assertEquals(1524.51, accounts[0].getBalance());
    }

    @Test
    public void withdrawalFeeTest(){
        accounts[1].withdraw(100);
        assertEquals(2136.60, accounts[1].getBalance());
    }
    
    @Test
    public void withdrawalInterestTest(){
        accounts[2].withdraw(2434.31);
        assertEquals(-2583.00, accounts[2].getBalance());
    }

    @Test
    public void withdrawalLimitTest(){
        accounts[2].withdraw(9990.31);
        assertEquals(-100.00, accounts[2].getBalance());
    }
    
    @Test
    public void depositChequingTest(){
        accounts[0].deposit(5000);
        assertEquals(6524.51, accounts[0].getBalance());
    }

    @Test
    public void depositLoanTest(){
        accounts[2].deposit(1000);
        assertEquals(900, accounts[2].getBalance());
    }

    @Test
    public void incomeTaxTest() {
        double income = 4000;
        accounts[0].deposit(income);
        ((Taxable)accounts[0]).tax(income);
        assertEquals(5374.51, accounts[0].getBalance());

    }

}
