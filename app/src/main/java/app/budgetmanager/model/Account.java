package app.budgetmanager.model;

public class Account {
    private String name, type, balance;
    private int id;

    public Account() {}

    public Account(int id, String name, String type, String balance) {
        this.id = id;
        this.name = name;
        this.type = type ;
        this.balance = balance;
    }

    public Account(String name, String type, String balance) {
        this.name = name;
        this.type = type ;
        this.balance = balance;
    }

    // Getters
    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public String getBalance() { return this.balance; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setBalance(String balance) { this.balance = balance; }
}
