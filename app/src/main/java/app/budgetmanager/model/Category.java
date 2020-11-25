package app.budgetmanager.model;

public class Category {
    String id;
    String name;
    String type;
    String account;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
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

    public Category(String id, String name, String type, String account) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.account = account;
    }

    public Category(String name, String type, String account) {
        this.name = name;
        this.type = type;
        this.account = account;
    }
}
