package com.example.tugaspraktikum8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.tugaspraktikum8.database.DatabaseHelper;
import com.example.tugaspraktikum8.model.Item;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItemDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public ItemDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    public long insertItem(Item item) {
        ContentValues values = new ContentValues();
        String currentTime = getCurrentDateTime();

        values.put(DatabaseHelper.COLUMN_TITLE, item.getTitle());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(DatabaseHelper.COLUMN_CREATED_AT, currentTime);
        values.put(DatabaseHelper.COLUMN_UPDATED_AT, currentTime);

        return database.insert(DatabaseHelper.TABLE_ITEMS, null, values);
    }

    public int updateItem(Item item) {
        ContentValues values = new ContentValues();
        String currentTime = getCurrentDateTime();

        values.put(DatabaseHelper.COLUMN_TITLE, item.getTitle());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(DatabaseHelper.COLUMN_UPDATED_AT, currentTime);

        return database.update(DatabaseHelper.TABLE_ITEMS, values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public int deleteItem(int id) {
        return database.delete(DatabaseHelper.TABLE_ITEMS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, null, null, null, null, null,
                DatabaseHelper.COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = cursorToItem(cursor);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public List<Item> searchItems(String query) {
        List<Item> items = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%"};

        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, null, selection, selectionArgs,
                null, null, DatabaseHelper.COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = cursorToItem(cursor);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public Item getItemById(int id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, null,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Item item = cursorToItem(cursor);
            cursor.close();
            return item;
        }
        return null;
    }

    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
        item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
        item.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CREATED_AT)));
        item.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UPDATED_AT)));
        return item;
    }
}