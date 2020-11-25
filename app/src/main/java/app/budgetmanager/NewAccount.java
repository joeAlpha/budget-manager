package app.budgetmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;

public class NewAccount extends AppCompatActivity {
    // Gui elements
    EditText accountNameField;
    Spinner accountTypeOptions;
    Button registerBtn;

    // Values to be inserted
    String accountName, accountType;
    private DatabaseHandler db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        db = new DatabaseHandler(this);

        // Account name field
        accountNameField = findViewById(R.id.accountNameField);

        // Account type dropdown list
        accountTypeOptions = (Spinner) findViewById(R.id.accountTypeOptions);
        String[] options = new String[]{"Credit", "Debit", "Money"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        accountTypeOptions.setAdapter(adapter);

        // Register button
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountName = accountNameField.getText().toString();
                accountType = accountTypeOptions.getSelectedItem().toString();

                // Insertion in DB
                db.addAccount(new Account(accountName, accountType));
                Toast.makeText(getApplicationContext(),accountName + " registered successfully!",Toast.LENGTH_SHORT).show();
            }

        });
    }
}
