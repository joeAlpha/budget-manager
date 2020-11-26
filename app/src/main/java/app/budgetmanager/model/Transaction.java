package app.budgetmanager.model;

public class Transaction {
    String id;
    String type;
    String date;
    String location;
    String concept;
    String beneficiary;
    String notes;
    String scheduled;
    String account;
    String category;

    public Transaction(String id, String type, String date, String location, String concept, String beneficiary, String notes, String scheduled, String account, String category) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.location = location;
        this.concept = concept;
        this.beneficiary = beneficiary;
        this.notes = notes;
        this.scheduled = scheduled;
        this.account = account;
        this.category = category;
    }

    public Transaction(String type, String date, String location, String concept, String beneficiary, String notes, String scheduled, String account, String category) {
        this.type = type;
        this.date = date;
        this.location = location;
        this.concept = concept;
        this.beneficiary = beneficiary;
        this.notes = notes;
        this.scheduled = scheduled;
        this.account = account;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getConcept() {
        return concept;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public String getNotes() {
        return notes;
    }

    public String getScheduled() {
        return scheduled;
    }

    public String getAccount() {
        return account;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
