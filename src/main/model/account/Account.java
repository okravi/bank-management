package src.main.model.account;

import java.text.DecimalFormat;

public abstract class Account {
    String id;
    String name;
    double balance;

    public Account(String id, String name, double balance) throws IllegalArgumentException{

        if (id == null || id.isBlank() || name == null || name.isBlank())
        throw new IllegalArgumentException("Fields can not be null or blank");  
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    
    public Account(Account source) {
        this.id = source.id;
        this.name = source.name;
        this.balance = source.balance;
    }

    public String getId() {
        return this.id;
    }

    public void setId (String id) throws IllegalArgumentException{
        if (id == null || id.isBlank())
        throw new IllegalArgumentException("Fields can not be null or blank");  
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (name == null || name.isBlank())
        throw new IllegalArgumentException("Fields can not be null or blank");  
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return (this.getClass().getSimpleName()) + "    " +
        "\t" + id + "" +
        "\t" + name + "" +
        "\t$" + balance + "";
    }

    public abstract boolean deposit(double amount);

    public abstract boolean withdraw(double amount);

    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(amount));
    }

    public abstract Account clone();

}
