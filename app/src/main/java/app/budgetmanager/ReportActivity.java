package app.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    TextView currentAccountLabel, balanceLabel;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_layout);
        db = new DatabaseHandler(this);
        account = db.getAccount(db.getActiveAccountId());

        // Status >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        currentAccountLabel = findViewById(R.id.current_account_label);
        currentAccountLabel.setText("Account: " + account.getName());
        balanceLabel = findViewById(R.id.account_balance_label);
        balanceLabel.setText("$" + account.getBalance());

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
                    "Account: " + transaction.getAccount() +
                            "\nCategory: " + transaction.getCategory() +
                            "\nAmmount: $" + transaction.getAmmount() +
                            "\nDate: " + transaction.getDate() +
                            "\nLocation: " + transaction.getLocation() +
                            "\nConcept: " + transaction.getConcept() +
                            "\nBeneficiary: " + transaction.getBeneficiary() +
                            "\nNotes: " + transaction.getNotes()
            );
        }
        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                transactionData
        );
        reportListView.setAdapter(reportsAdapter);
    }
}
