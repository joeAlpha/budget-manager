package app.budgetmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;

import java.util.ArrayList;
import java.util.List;

// An activity for use others user's account
public class ManageCategory extends AppCompatActivity {
    Spinner categoryOptionsSpin;
    EditText newCategoryNameField;
    Button saveNewCategoryButton;
    String categoryName, categoryType;
    DatabaseHandler db;
    ListView categoryList;
    Account account;
    TextView currentAccountLabel, balanceLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_category);

        db = new DatabaseHandler(this);
        account = db.getAccount(db.getActiveAccountId());


        // General account status
        currentAccountLabel = findViewById(R.id.current_account_label);
        currentAccountLabel.setText("Account: " + account.getName());
        balanceLabel = findViewById(R.id.account_balance_label);
        balanceLabel.setText("$" + account.getBalance());

        newCategoryNameField = findViewById(R.id.new_category_name_field);

        categoryOptionsSpin = (Spinner) findViewById(R.id.category_type_spin);
        String[] options = new String[]{"Payment", "Expenses"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        categoryOptionsSpin.setAdapter(adapter);

        saveNewCategoryButton = findViewById(R.id.save_new_category_button);

        saveNewCategoryButton = findViewById(R.id.save_new_category_button);
        saveNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = newCategoryNameField.getText().toString();
                categoryType = categoryOptionsSpin.getSelectedItem().toString();

                // Insertion in DB
                db.addCategory(new Category(categoryName, categoryType, account.getId()));
                Toast.makeText(getApplicationContext(),categoryName + " registered successfully!",Toast.LENGTH_SHORT).show();
            }
        });

        categoryList = (ListView) findViewById(R.id.category_list);
        List<Category> categories = db.getAllCategories();
        List<String> categoryData = new ArrayList<String>();
        for(Category category : categories) {
            categoryData.add(category.getName() + " :: " + category.getType());
        }
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryData);
        categoryList.setAdapter(categoriesAdapter);
    }
}
