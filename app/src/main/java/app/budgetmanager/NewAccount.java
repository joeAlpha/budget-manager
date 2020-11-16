package app.budgetmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NewAccount extends AppCompatActivity {
    Spinner accountTypeOptions;
    Button registerBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        accountTypeOptions = (Spinner) findViewById(R.id.accountTypeOptions);
        String[] options = new String[] {"Credit", "Debit", "Money"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, (List<String>) accountTypeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeOptions.setAdapter(adapter);
    }
}
