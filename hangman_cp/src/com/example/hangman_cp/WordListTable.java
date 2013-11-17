package com.example.hangman_cp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WordListTable {
	  public static final String TABLE_WORD_LIST = "wordlist";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_WORD = "word";
	  public static final String COLUMN_LOCALE = "locale";

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_WORD_LIST
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_WORD + " text not null, " 
	      + COLUMN_LOCALE + " text" 
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(WordListTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD_LIST);
	    onCreate(database);
	  }
}
