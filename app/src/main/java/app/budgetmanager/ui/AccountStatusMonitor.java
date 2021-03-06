package app.budgetmanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import app.budgetmanager.R;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;

public class AccountStatusMonitor extends Fragment {
    TextView currentAccountLabel, accountBalanceLabel;
    DatabaseHandler db;
    Account account;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_status_layout, container, true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHandler(getContext());
        if (db.getAccountsCount() == 0) {
            db.addAccount(new Account("Default", "Money"));
            db.addCategory(new Category("MyExpense", "Expenses", "1"));
            db.addCategory(new Category("MyPayment", "Payment", "1"));
            db.setActiveAccount("1");
        }

        account = db.getAccount(db.getActiveAccountId());

        currentAccountLabel = view.findViewById(R.id.current_account_label);
        currentAccountLabel.setText("Account: Default"+ account.getName());
        accountBalanceLabel = view.findViewById(R.id.account_balance_label);
        accountBalanceLabel.setText("$" + account.getBalance());

    }

    @Override
    public void onResume() {
        super.onResume();
        account = db.getAccount(db.getActiveAccountId());
        currentAccountLabel.setText("Account: " + account.getName());
        accountBalanceLabel.setText("$" + account.getBalance());
    }

    public void refreshAccount() {
        account = db.getAccount(db.getActiveAccountId());
        currentAccountLabel.setText("Account: " + account.getName());
    }

}
