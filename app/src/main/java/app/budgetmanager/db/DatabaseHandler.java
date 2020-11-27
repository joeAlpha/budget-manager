package app.budgetmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.budgetmanager.model.Transaction;
import app.budgetmanager.model.Account;
import app.budgetmanager.model.Category;

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

    // Active account table
    private static final String ACTIVE_ACCOUNT_TABLE = "active_accounts";
    private static final String ACTIVE_ACCOUNT_ID = "id";
    private static final String ACTIVE_ACCOUNT = "active_account";

    // Transaction (Includes deposits n transfers) table fields
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String TRANSACTION_ID = "id";
    private static final String TRANSACTION_AMMOUNT = "ammount";
    private static final String TRANSACTION_TYPE = "type";
    private static final String TRANSACTION_DATE = "date";
    private static final String TRANSACTION_LOCATION = "location";
    private static final String TRANSACTION_CONCEPT = "concept";
    private static final String TRANSACTION_BENEFICIARY = "beneficiary";
    private static final String TRANSACTION_SCHEDULED = "scheduled";
    private static final String TRANSACTION_NOTES = "notes";
    private static final String TRANSACTION_ACCOUNT = "account";
    private static final String TRANSACTION_CATEGORY = "category";

    //Categories
    private static final String CATEGORY_TABLE = "categories";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_TYPE = "type";
    private static final String CATEGORY_ACCOUNT = "account";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    //multiple creations?
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS_TABLE + "(" +
                ACCOUNT_ID + " INTEGER PRIMARY KEY NOT NULL," +
                ACCOUNT_NAME + " TEXT," +
                ACCOUNT_TYPE + " TEXT," +
                ACCOUNT_BALANCE + " NUMERIC" +
                ")";

        String CREATE_ACTIVE_ACCOUNT_TABLE = "CREATE TABLE " + ACTIVE_ACCOUNT_TABLE + "(" +
                ACTIVE_ACCOUNT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ACTIVE_ACCOUNT + " TEXT" +
                ")";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TRANSACTIONS_TABLE + "(" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY NOT NULL," +
                TRANSACTION_AMMOUNT + " TEXT," +
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

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORY_TABLE + "(" +
                CATEGORY_ID + " INTEGER PRIMARY KEY NOT NULL," +
                CATEGORY_NAME + " TEXT," +
                CATEGORY_TYPE + " TEXT, " +
                CATEGORY_ACCOUNT + " TEXT" +
                ")";

        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_ACTIVE_ACCOUNT_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVE_ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);

        // Create tables again
        onCreate(db);
    }

    // ACCOUNT METHODS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    public void setActiveAccount(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACTIVE_ACCOUNT, id);

        db.insert(ACTIVE_ACCOUNT_TABLE, null , values);
    }

    public String getActiveAccountId() {
        SQLiteDatabase db = this.getReadableDatabase();

        //String activeAccountQuery = "SELECT " + ACTIVE_ACCOUNT + " FROM " + ACTIVE_ACCOUNT_TABLE;
        //Cursor cursor = db.rawQuery(activeAccountQuery, null);
        Cursor cursor = db.query(
                ACTIVE_ACCOUNT_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);
        //Log.d("Count",String.valueOf(cursor.getCount()));

        String activeAccountId;
        if (cursor != null && cursor.moveToFirst()) {
            activeAccountId = cursor.getString(1);
            cursor.close();
            return activeAccountId;
        } else return "THERE IS NO ACTIVE ACCOUNTS FOUND";
    }

    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getName()); // Contact Name
        values.put(ACCOUNT_TYPE, account.getType()); // Contact Phone
        values.put(ACCOUNT_BALANCE, account.getBalance()); // Contact Phone

        db.insert(ACCOUNTS_TABLE, null, values);
    }

    public Account getAccount(String id) {
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
                        id},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Account account = new Account(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            cursor.close();
            return account;
        } else {
            Log.d("FAIL REQUEST","Can't retrieve the requested account");
            return null;
        }
    }

    public List<String> getAllAccountsNames() {
        List<String> accountNames = new ArrayList<String>();
        String selectQuery = "SELECT name FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                accountNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return accountNames;

    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<Account>();
        String selectQuery = "SELECT  * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(cursor.getString(0));
                account.setName(cursor.getString(1));
                account.setType(cursor.getString(2));
                account.setBalance(cursor.getString(3));

                accountList.add(account);
            } while (cursor.moveToNext());
        }

        return accountList;
    }

    public void updateBalance(String id, String ammount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_BALANCE, ammount);

        db.update(ACCOUNTS_TABLE, values, ACCOUNT_ID + " = ?",
                new String[]{id});
    }

    public void deleteAccount(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNTS_TABLE, ACCOUNT_ID + " = ?",
                new String[]{id});
    }

    public int getAccountsCount() {
        String countQuery = "SELECT * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count;
        if (cursor.getCount() > 0) {
            count = cursor.getCount();
            cursor.close();
            return count;
        } else return 0;
    }
    // |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    // CATEGORIES METHODS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());
        values.put(CATEGORY_TYPE, category.getType());
        values.put(CATEGORY_ACCOUNT, category.getAccount());

        db.insert(CATEGORY_TABLE, null, values);
    }

    public Category getCategory(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                ACCOUNTS_TABLE,
                new String[]{
                        CATEGORY_ID,
                        CATEGORY_NAME,
                        CATEGORY_ACCOUNT
                },
                CATEGORY_ID + "=?",
                new String[]{
                        id},
                null,
                null,
                null,
                null
        );
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2)
        );

        return category;
    }

    public List<String> getAllCategoriesNames() {
        List<String> categoriesNames = new ArrayList<String>();
        String selectQuery = "SELECT name FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                categoriesNames.add(cursor.getString(2));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return categoriesNames;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getString(0));
                category.setName(cursor.getString(1));
                category.setType(cursor.getString(2));
                category.setAccount(cursor.getString(3));
                categoryList.add(category);
                Log.d("Fetched: ", category.getName());
            } while (cursor.moveToNext());
        }

        return categoryList;
    }

    public int updateCategory(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getName());
        values.put(ACCOUNT_TYPE, account.getName());
        values.put(ACCOUNT_BALANCE, account.getBalance());

        return db.update(ACCOUNTS_TABLE, values, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
    }

    public void deleteCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE, CATEGORY_ID + " = ?",
                new String[]{id});
    }

    public void deleteAccountCategories(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE, CATEGORY_ACCOUNT + " = ?",
                new String[]{id});
    }

    public int getCategoriesCount() {
        String countQuery = "SELECT  * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


    // TRANSACTIONS METHODS ||||||||||||||||||||||||||||||||||||||||||
    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRANSACTION_AMMOUNT, transaction.getAmmount());
        values.put(TRANSACTION_TYPE, transaction.getType());
        values.put(TRANSACTION_DATE, transaction.getDate());
        values.put(TRANSACTION_LOCATION, transaction.getLocation());
        values.put(TRANSACTION_CONCEPT, transaction.getConcept());
        values.put(TRANSACTION_BENEFICIARY, transaction.getBeneficiary());
        values.put(TRANSACTION_NOTES, transaction.getNotes());
        values.put(TRANSACTION_SCHEDULED, transaction.getScheduled());
        values.put(TRANSACTION_ACCOUNT, transaction.getAccount());
        values.put(TRANSACTION_CATEGORY, transaction.getCategory());

        db.insert(TRANSACTIONS_TABLE, null, values);
    }

    public Category getTransaction(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                ACCOUNTS_TABLE,
                new String[]{
                        CATEGORY_ID,
                        CATEGORY_NAME,
                        CATEGORY_ACCOUNT
                },
                CATEGORY_ID + "=?",
                new String[]{
                        id},
                null,
                null,
                null,
                null
        );
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2)
        );

        return category;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT * FROM " + TRANSACTIONS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                );
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }

        return transactionList;
    }

    /*
    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getName());
        values.put(ACCOUNT_TYPE, account.getName());
        values.put(ACCOUNT_BALANCE, account.getBalance());

        return db.update(ACCOUNTS_TABLE, values, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
    }

    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNTS_TABLE, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
    }

    public int getTransactionCount() {
        String countQuery = "SELECT  * FROM " + ACCOUNTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
     */

}

