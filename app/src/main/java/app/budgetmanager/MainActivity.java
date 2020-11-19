package app.budgetmanager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent intentCategories = new Intent(MainActivity.this, Categorie.class);
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