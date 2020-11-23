package app.budgetmanager.model;

public class Category {
    String id, name, account;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public Category() {}

    public Category(String id, String name, String account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }

    public Category(String name, String account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }
}
