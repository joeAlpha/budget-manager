package app.budgetmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Transfer extends AppCompatActivity {
    DatabaseHandler db;
    Spinner accountNameSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_layout);

        db = new DatabaseHandler(this);
        List<String> accountNames = db.getAllAccountsNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,accountNames);
        accountNameSpin = (Spinner) findViewById(R.id.accountForTransfer);
        accountNameSpin.setAdapter(adapter);
    }
}
