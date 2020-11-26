package app.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;
import app.budgetmanager.model.Transaction;

import java.util.ArrayList;
import java.util.List;

// An activity for view the account's reports
public class ReportActivity extends AppCompatActivity {
    ListView reportListView;
    Spinner reportFilterSpin, reportAccountSpin;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_layout);
        db = new DatabaseHandler(this);

        reportAccountSpin = findViewById(R.id.report_account_spin);
        List<Account> accounts = db.getAllAccounts();
        List<String> accountsData = new ArrayList<String>();
        for (Account account : accounts) {
            accountsData.add(account.getName() + " (" + account.getType() + ")");
        }
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                accountsData
        );
        reportAccountSpin.setAdapter(accountsAdapter);

        reportFilterSpin = findViewById(R.id.report_filter_spin);
        String[] options = new String[]{"Monthly", "Expenses"};
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                options
        );
        reportFilterSpin.setAdapter(filterAdapter);

        reportListView = findViewById(R.id.report_list);
        List<Transaction> transactions = db.getAllTransactions();
        List<String> transactionData = new ArrayList<String>();
        for (Transaction transaction : transactions) {
            transactionData.add(
                    "Concept: " + transaction.getConcept() +
                            "\nType: " + transaction.getType() +
                            "\nDate: " + transaction.getDate());
        }
        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                transactionData
        );
        reportListView.setAdapter(reportsAdapter);
    }
}
