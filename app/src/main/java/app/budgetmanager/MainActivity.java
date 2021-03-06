package app.budgetmanager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// A main view with the actions for each account
public class MainActivity extends AppCompatActivity {
    Button transactionBtn,
            reportBtn,
            categoriesBtn,
            accountManagerBtn,
            logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        categoriesBtn = findViewById(R.id.categoriesBtn);
        categoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCategories = new Intent(MainActivity.this, ManageCategory.class);
                startActivity(intentCategories);
            }
        });

        accountManagerBtn = findViewById(R.id.account_manager_btn);
        accountManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSwitchAccount = new Intent(MainActivity.this, AccountManager.class);
                startActivity(intentSwitchAccount);
            }

        });

        logoutBtn = findViewById(R.id.logoutBtn);
    }
}