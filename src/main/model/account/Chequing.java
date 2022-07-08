package src.main.model.account;

public class Chequing extends Account{

    private static final double OVERDRAFT_FEE = 5.50;
    private static final double OVERDRAFT_LIMIT = -200;
    

    public Chequing(String id, String name, double balance) {
        super(id, name, balance);
    } 

    public Chequing(Chequing source) {
        super(source);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(round(super.getBalance()+amount));     
    }

    @Override
    public boolean withdraw(double amount) {
        if (super.getBalance() - amount > 0) {
            super.setBalance(round(super.getBalance()-amount));
        } else if (super.getBalance() - amount - OVERDRAFT_FEE >= OVERDRAFT_LIMIT){
            super.setBalance(round(super.getBalance()-amount - OVERDRAFT_FEE));
        } else {
            return false;
        }
        
        return true;
    } 


}
