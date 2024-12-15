package com.nibm.mydiaryapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "diary.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "entries"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TITLE TEXT, " +
                "$COL_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Method to insert a new entry
    fun insertEntry(title: String, content: String): Long {
        val db = writableDatabase
        val values = android.content.ContentValues()
        values.put(COL_TITLE, title)
        values.put(COL_CONTENT, content)
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllEntries(): List<Pair<String, String>> {
        val db = readableDatabase
        val entries = mutableListOf<Pair<String, String>>()
        val cursor = db.rawQuery("SELECT $COL_TITLE, $COL_CONTENT FROM $TABLE_NAME", null)
        val titleColumnIndex = cursor.getColumnIndex(COL_TITLE)
        val contentColumnIndex = cursor.getColumnIndex(COL_CONTENT)

        if (titleColumnIndex == -1 || contentColumnIndex == -1) {
            cursor.close()
            return emptyList()
        }

        // Now iterate over the cursor
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(titleColumnIndex)
                val content = cursor.getString(contentColumnIndex)
                entries.add(Pair(title, content))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return entries
    }
    fun updateEntry(id: Long, newTitle: String, newContent: String): Int {
        val db = writableDatabase
        val values = android.content.ContentValues()
        values.put(COL_TITLE, newTitle)
        values.put(COL_CONTENT, newContent)
        val whereClause = "$COL_ID = ?"
        val whereArgs = arrayOf(id.toString())

        return db.update(TABLE_NAME, values, whereClause, whereArgs)
    }


}