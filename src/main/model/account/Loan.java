package src.main.model.account;

public class Loan extends Account{

    private static final double INTEREST_FEE = 1.02;
    private static final double MAX_DEBT = -10000;


    public Loan(String id, String name, double balance) {
        super(id, name, balance);
    } 

    public Loan(Loan source) {
        super(source);
    }

    @Override
    public boolean deposit(double amount) {
        super.setBalance(round(super.getBalance()+amount));
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        
        
       if (super.getBalance() - amount*INTEREST_FEE > MAX_DEBT) {
            super.setBalance(round(super.getBalance()-amount*INTEREST_FEE));
        } else {
            return false;
        }
        
        return true;
    } 

    @Override
    public Account clone() {
        Account copy = new Loan(this);
        return copy;
    } 

}
