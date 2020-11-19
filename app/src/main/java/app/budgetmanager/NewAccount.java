package app.budgetmanager;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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
    EditText accountNameField;
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
        String[] options = new String[]{"Credit", "Debit", "Money"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_spinner_item, (List<String>) accountTypeOptions);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //accountTypeOptions.setAdapter(adapter)
        accountTypeOptions.setAdapter(adapter);

        // Register button
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountName = accountNameField.toString();
                accountType = accountTypeOptions.getSelectedItem().toString();

                // Insertion in DB
                db.addAccount(new Account(accountName, accountType, initialBalance));

                Log.d("Reading: ","Reading all contacts..");
                List<Account> accounts = db.getAllAccounts();

                for(Account account :accounts)
                {
                    String log = "Id: " + account.getId() + " ,Name: " + account.getName() + " , type: " +
                            account.getType();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }
            }

        });
    }
}
