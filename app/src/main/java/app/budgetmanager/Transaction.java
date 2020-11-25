package app.budgetmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends AppCompatActivity {
    Spinner categorySpin, accountSpin, transactionType;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_layout);

        db = new DatabaseHandler(this);

        transactionType = (Spinner) findViewById(R.id.transaction_type);
        String[] options = new String[]{"Deposit", "Transfer"};
        ArrayAdapter<String> transactionTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        transactionType.setAdapter(transactionTypeAdapter);

        accountSpin = (Spinner) findViewById(R.id.account_spin);
        List<Account> accounts = db.getAllAccounts();
        List<String> accountsData = new ArrayList<String>();
        for(Account account : accounts) {
            accountsData.add(account.getName() + " | " + account.getType());
        }
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountsData);
        accountSpin.setAdapter(accountsAdapter);

        categorySpin = (Spinner) findViewById(R.id.category_spin);
        List<Category> categories = db.getAllCategories();
        List<String> categoryData = new ArrayList<String>();
        for(Category category : categories) {
            categoryData.add(category.getName() + " | " + category.getType());
        }
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryData);
        categorySpin.setAdapter(categoriesAdapter);
    }
}
