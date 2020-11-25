package app.budgetmanager.ui;

import android.content.Context;
import android.widget.TextView;
import app.budgetmanager.R;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;

public class AccountStatusMonitor {
    public void getStatus(Context activityContext, TextView accountName, TextView balance) {
        DatabaseHandler db = new DatabaseHandler(activityContext);
        Account activeAccount = db.getAccount(db.getActiveAccountId());
        accountName.setText("Account:" + activeAccount.getName());
        balance.setText("$" + activeAccount.getBalance());
    }
}
