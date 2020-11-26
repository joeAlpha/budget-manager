package app.budgetmanager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;
import app.budgetmanager.ui.AccountStatusMonitor;

// A main view with the actions for each account
public class MainActivity extends AppCompatActivity {
    Button transactionBtn,
            reportBtn,
            depositBtn,
            scheduledPaidsBtn,
            categoriesBtn,
            switchAccountBtn,
            logoutBtn,
            newAccountBtn;

    TextView currentAccountLabel, balanceLabel;
    DatabaseHandler db;
    AccountStatusMonitor accountStatusMonitor;
    Account activeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        // Executes once the first start up
        if (db.getAccountsCount() == 0) {
            db.addAccount(new Account("Default", "Money"));
            db.addCategory(new Category("MyExpense", "Expenses", "1"));
            db.addCategory(new Category("MyPayment", "Payment", "1"));
            db.setActiveAccount("1");
        }

        activeAccount = db.getAccount(db.getActiveAccountId());

        // General account status
        currentAccountLabel = findViewById(R.id.currentAccount);
        currentAccountLabel.setText("Account: " + activeAccount.getName());
        balanceLabel = findViewById(R.id.balance);
        balanceLabel.setText("$" + activeAccount.getBalance());

        reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReports = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intentReports);
            }
        });

        transactionBtn = findViewById(R.id.transaction_button);
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTransaction = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intentTransaction);
            }
        });

        scheduledPaidsBtn = findViewById(R.id.schedulePaidsBtn);
        scheduledPaidsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentScheduledPaids = new Intent(MainActivity.this, ScheduledPaid.class);
                startActivity(intentScheduledPaids);
            }
        });

        categoriesBtn = findViewById(R.id.categoriesBtn);
        categoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCategories = new Intent(MainActivity.this, ManageCategory.class);
                startActivity(intentCategories);
            }
        });

        switchAccountBtn = findViewById(R.id.switchAccountBtn);
        switchAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSwitchAccount = new Intent(MainActivity.this, AccountChooser.class);
                startActivity(intentSwitchAccount);
            }

        });

        newAccountBtn = findViewById(R.id.newAccountBtn);
        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccountIntent = new Intent(MainActivity.this, NewAccount.class);
                startActivity(newAccountIntent);
            }
        });
        logoutBtn = findViewById(R.id.logoutBtn);
    }
}