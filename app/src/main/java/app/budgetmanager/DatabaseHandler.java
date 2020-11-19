package app.budgetmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.budgetmanager.model.Account;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budgetManager";

    // New account table fields
    private static final String ACCOUNTS_TABLE = "accounts";
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_NAME = "name";
    private static final String ACCOUNT_TYPE = "type";
    private static final String ACCOUNT_BALANCE = "balance";

    // Transaction (Includes deposits n transfers) table fields
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String TRANSACTION_ID = "id";
    private static final String TRANSACTION_TYPE = "type";
    private static final String TRANSACTION_DATE = "date";
    private static final String TRANSACTION_LOCATION = "location";
    private static final String TRANSACTION_CONCEPT = "concept";
    private static final String TRANSACTION_BENEFICIARY = "beneficiary";
    private static final String TRANSACTION_SCHEDULED = "scheduled";
    private static final String TRANSACTION_NOTES = "notes";
    private static final String TRANSACTION_ACCOUNT = "account";
    private static final String TRANSACTION_CATEGORY = "account";

    //Categories
    private static final String CATEGORY_TABLE = "categories";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_ACCOUNT = "account";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS = "CREATE TABLE " + ACCOUNTS_TABLE + "(" +
                ACCOUNT_ID + " INTEGER PRIMARY KEY," +
                ACCOUNT_NAME + " TEXT," +
                ACCOUNT_TYPE + " TEXT," +
                ACCOUNT_BALANCE + " NUMERIC" +
                ")";
        db.execSQL(CREATE_ACCOUNTS);

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TRANSACTIONS_TABLE + "(" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY," +
                TRANSACTION_TYPE + " TEXT," +
                TRANSACTION_DATE + " TEXT," +
                TRANSACTION_LOCATION + " TEXT," +
                TRANSACTION_CONCEPT + " TEXT, " +
                TRANSACTION_BENEFICIARY + " TEXT," +
                TRANSACTION_SCHEDULED + " TEXT," +
                TRANSACTION_NOTES + " TEXT," +
                TRANSACTION_ACCOUNT + " TEXT," +
                TRANSACTION_CATEGORY + " TEXT" +
                ")";
        db.execSQL(CREATE_TRANSACTION_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORY_TABLE + "(" +
                CATEGORY_ID + " INTEGER PRIMARY KEY," +
                CATEGORY_NAME + " TEXT," +
                TRANSACTION_ACCOUNT + " TEXT" +
                ")";
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);

        // Create tables again
        onCreate(db);
    }

    // ACCOUNT METHODS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getName()); // Contact Name
        values.put(ACCOUNT_TYPE, account.getType()); // Contact Phone
        values.put(ACCOUNT_BALANCE, account.getBalance()); // Contact Phone

        db.insert(ACCOUNTS_TABLE, null, values);
        db.close();
    }

    Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                ACCOUNTS_TABLE,
                new String[]{
                        ACCOUNT_ID,
                        ACCOUNT_NAME,
                        ACCOUNT_TYPE,
                        ACCOUNT_BALANCE
                },
                ACCOUNT_ID + "=?",
                new String[]{
                        String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        if (cursor != null)
            cursor.moveToFirst();

        Account account = new Account(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return account;
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<Account>();
        String selectQuery = "SELECT  * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setName(cursor.getString(1));
                account.setType(cursor.getString(2));
                account.setBalance(cursor.getString(3));

                accountList.add(account);
            } while (cursor.moveToNext());
        }

        return accountList;
    }

    public int updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getName());
        values.put(ACCOUNT_TYPE, account.getName());
        values.put(ACCOUNT_BALANCE, account.getBalance());

        return db.update(ACCOUNTS_TABLE, values, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
    }

    public void deleteAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNTS_TABLE, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
        db.close();
    }

    public int getAccountsCount() {
        String countQuery = "SELECT  * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    // |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    // TRANSACTIONS METHODS ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

}

