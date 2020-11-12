package app.budgetmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// An activity for use others user's account
public class AccountChooser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_chooser_layout);
    }
}
