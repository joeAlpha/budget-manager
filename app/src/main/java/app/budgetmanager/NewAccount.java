package app.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.model.Account;

import java.util.List;

public class NewAccount extends AppCompatActivity {
    // Gui elements
    Editable accountNameField;
    Spinner accountTypeOptions;
    Button registerBtn;

    // Values to be inserted
    String accountName, accountType;
    final String initialBalance = "1000.00";
    private DatabaseHandler db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        db = new DatabaseHandler(this);

        // Account name field
        accountNameField = findViewById(R.id.accountNameField);

        // Account type dropdown list
        accountTypeOptions = (Spinner) findViewById(R.id.accountTypeOptions);
        String[] options = new String[] {"Credit", "Debit", "Money"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, (List<String>) accountTypeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeOptions.setAdapter(adapter);

        // Register button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountName = accountNameField.toString();
                accountType = accountTypeOptions.getSelectedItem().toString();

                // Insertion in DB
                db.addAccount(new Account(accountName, accountType, initialBalance));
            }
        });
    }
}
