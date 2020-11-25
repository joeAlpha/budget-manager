package app.budgetmanager.model;

public class Account {
    private String id, name, type, balance;

    public Account() {}

    public Account(String id, String name, String type, String balance) {
        this.id = id;
        this.name = name;
        this.type = type ;
        this.balance = balance;
    }

    public Account(String name, String type) {
        this.name = name;
        this.type = type ;
        this.balance = "1000.00";
    }

    // Getters
    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public String getBalance() { return this.balance; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setBalance(String balance) { this.balance = balance; }
}
