package app.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.model.Account;

import java.util.List;

// An activity for use others user's account
public class AccountChooser extends AppCompatActivity {
    DatabaseHandler db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_chooser);

        listView = (ListView) findViewById(R.id.accounts_list);
        db = new DatabaseHandler(this);
        List<String> accounts = db.getAllAccountsNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accounts);
        listView.setAdapter(adapter);
    }
}
