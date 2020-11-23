package app.budgetmanager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;

import java.util.List;

// A main view with the actions for each account
public class MainActivity extends AppCompatActivity {
    Button transferBtn,
            reportBtn,
            depositBtn,
            scheduledPaidsBtn,
            categoriesBtn,
            switchAccountBtn,
            logoutBtn,
            newAccountBtn;

    TextView currentAccountLabel, balanceLabel;
    DatabaseHandler db;
    String activeAccountId;
    Account activeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        //Log.d("Accounts count: ", String.valueOf(db.getAccountsCount()));
        if (db.getAccountsCount() == 0) {
            db.addAccount(new Account("Default", "Money", "1000.00"));
            /*
            List<Account> test = db.getAllAccounts();
            for (Account account : test) {
                Log.d("Account id: ", account.getId());
            }

             */
            db.setActiveAccount("1");
        }
        //Log.d("Accounts count: ", String.valueOf(db.getAccountsCount()));
        //Log.d("Active account id: ", db.getActiveAccountId());
        String activeAccountId = db.getActiveAccountId();
        Log.d("TEST getactiveaccount()", activeAccountId);
        activeAccount = db.getAccount(activeAccountId);

        currentAccountLabel = findViewById(R.id.currentAccount);
        currentAccountLabel.setText(activeAccount.getName());

        balanceLabel = findViewById(R.id.balance);
        balanceLabel.setText(activeAccount.getBalance());

        reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReports = new Intent(MainActivity.this, Report.class);
                startActivity(intentReports);
            }
        });

        transferBtn = findViewById(R.id.transferBtn);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTransfer = new Intent(MainActivity.this, Transfer.class);
                startActivity(intentTransfer);
            }
        });

        depositBtn = findViewById(R.id.depositBtn);
        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDeposit = new Intent(MainActivity.this, Deposit.class);
                startActivity(intentDeposit);
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
                Intent intentCategories = new Intent(MainActivity.this, Categories.class);
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