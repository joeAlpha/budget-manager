package app.budgetmanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import app.budgetmanager.db.DatabaseHandler;
import app.budgetmanager.location.Constant;
import app.budgetmanager.location.GpsUtils;
import app.budgetmanager.model.*;
import app.budgetmanager.ui.AccountStatusMonitor;
import com.google.android.gms.location.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {
    // UI elements
    Spinner categorySpin, accountSpin, transactionType;
    DatabaseHandler db;
    Button okBtn;
    EditText ammountField, conceptField, beneficiaryField, notesField;
    CheckedTextView scheduledCheck;
    Account account;
    AccountStatusMonitor accountStatusMonitor;

    // Location variables
    private FusedLocationProviderClient mFusedLocationClient;
    int locationRequestCode = 1000;
    double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private String coordinates;
    private StringBuilder stringBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_layout);

        // Database >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        db = new DatabaseHandler(this);
        account = db.getAccount(db.getActiveAccountId());

        // Status >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        accountStatusMonitor = new AccountStatusMonitor();

        // Location initialization >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        if (!isContinue) {
                            coordinates = String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude);
                            Log.d("Location fetched: ", coordinates);
                        } else {
                            stringBuilder.append(wayLatitude);
                            stringBuilder.append("-");
                            stringBuilder.append(wayLongitude);
                            stringBuilder.append("\n\n");
                            coordinates = stringBuilder.toString();
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };

        getLocation();

        ammountField = findViewById(R.id.ammount_field);

        // Transaction's type
        transactionType = (Spinner) findViewById(R.id.transaction_type);
        String[] options = new String[]{"Deposit", "Transfer"};
        ArrayAdapter<String> transactionTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        transactionType.setAdapter(transactionTypeAdapter);

        // Transaction's account
        accountSpin = (Spinner) findViewById(R.id.account_spin);
        final List<Account> accounts = db.getAllAccounts();
        List<String> accountsData = new ArrayList<String>();
        for (Account account : accounts) {
            accountsData.add(account.getName() + " | " + account.getType());
        }
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountsData);
        accountSpin.setAdapter(accountsAdapter);

        // Transaction's category
        categorySpin = (Spinner) findViewById(R.id.category_spin);
        List<Category> categories = db.getAllCategories();
        List<String> categoryData = new ArrayList<String>();
        for (Category category : categories) {
            categoryData.add(category.getName() + " (" + category.getType() + ")");
        }
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryData);
        categorySpin.setAdapter(categoriesAdapter);

        ammountField = findViewById(R.id.ammount_field);
        conceptField = findViewById(R.id.concept_field);
        beneficiaryField = findViewById(R.id.beneficiary_field);
        notesField = findViewById(R.id.notes_field);
        scheduledCheck = findViewById(R.id.schedule_transaction_check);
        scheduledCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduledCheck.setChecked(!scheduledCheck.isChecked());
                //TODO: change icon
            }
        });

        // Transaction's button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        okBtn = findViewById(R.id.procced_with_transaction);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount = ammountField.getText().toString();

                // Type
                String type = transactionType.getSelectedItem().toString();

                // Date n' time
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
                String currentDateAndTime = sdf.format(new Date());

                // Concept
                String concept = conceptField.getText().toString();

                // Beneficiary
                String beneficiary = beneficiaryField.getText().toString();

                // Notes
                String notes = notesField.getText().toString();

                // Scheduled (boolean)
                String scheduled = scheduledCheck.isChecked() ? "true" : "false";

                // Account
                String accountId = account.getId();

                // Category
                String category = categorySpin.getSelectedItem().toString();

                Transaction transaction = new Transaction(
                        ammount,
                        type,
                        currentDateAndTime,
                        coordinates,
                        concept,
                        beneficiary,
                        notes,
                        scheduled,
                        accountId,
                        category
                );

                String currentBalanceOfTarget = db.getAccount(accountId).getBalance();
                if (transaction.getType().equals("Deposit")) {
                    String newBalance = String.valueOf(
                            Integer.parseInt(currentBalanceOfTarget) - Integer.parseInt(ammount)
                    );
                    db.updateBalance(accountId, newBalance);
                    db.addTransaction(transaction);
                    Toast.makeText(
                            getApplicationContext(), "" +
                                    "Transaction registered successfully!",
                            Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(currentBalanceOfTarget) > Integer.parseInt(ammount)) {
                    String newBalance = String.valueOf(
                            Integer.parseInt(currentBalanceOfTarget) - Integer.parseInt(ammount)
                    );
                    db.updateBalance(accountId, newBalance);
                    db.addTransaction(transaction);
                    accountStatusMonitor.refreshStatus();
                    Toast.makeText(
                            getApplicationContext(),
                            "transaction registered successfully!",
                            Toast.LENGTH_SHORT).show();
                    accountStatusMonitor.refreshStatus();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Error, the account haven't enough money to expense!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(TransactionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(TransactionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TransactionActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constant.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(TransactionActivity.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        coordinates = String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude);
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            coordinates = String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude);
                        }
                    });
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
