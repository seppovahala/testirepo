package com.example.hangman_cp;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordListAddActivity extends Activity {

	  private EditText mTitleText;

	  private Uri todoUri;

	  @Override
	  protected void onCreate(Bundle bundle) {
	    super.onCreate(bundle);
	    setContentView(R.layout.activity_word_list_add);

	    mTitleText = (EditText) findViewById(R.id.word_add);

	    Button confirmButton = (Button) findViewById(R.id.word_add_button);

	    Bundle extras = getIntent().getExtras();

	    // check from the saved Instance
	    todoUri = (bundle == null) ? null : (Uri) bundle
	        .getParcelable(WordListContentProvider.CONTENT_ITEM_TYPE);

	    // Or passed from the other activity
	    if (extras != null) {
	      todoUri = extras
	          .getParcelable(WordListContentProvider.CONTENT_ITEM_TYPE);

	      fillData(todoUri);
	    }

	    confirmButton.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View view) {
	        if (TextUtils.isEmpty(mTitleText.getText().toString())) {
	          makeToast();
	        } else {
	          setResult(RESULT_OK);
	          finish();
	        }
	      }

	    });
	  }

	  private void fillData(Uri uri) {
	    String[] projection = { WordListTable.COLUMN_WORD };
	    Cursor cursor = getContentResolver().query(uri, projection, null, null,
	        null);
	    if (cursor != null) {
	      cursor.moveToFirst();

	      mTitleText.setText(cursor.getString(cursor
	          .getColumnIndexOrThrow(WordListTable.COLUMN_WORD)));

	      // always close the cursor
	      cursor.close();
	    }
	  }

	  protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    saveState();
	    outState.putParcelable(WordListContentProvider.CONTENT_ITEM_TYPE, todoUri);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    saveState();
	  }

	  private void saveState() {
	    String summary = mTitleText.getText().toString();


	    ContentValues values = new ContentValues();
	    values.put(WordListTable.COLUMN_WORD, summary);

	    if (todoUri == null) {
	      // New todo
	      todoUri = getContentResolver().insert(WordListContentProvider.CONTENT_URI, values);
	    } else {
	      // Update todo
	      getContentResolver().update(todoUri, values, null, null);
	    }
	  }

	  private void makeToast() {
	    Toast.makeText(WordListAddActivity.this, "Please maintain a summary",
	        Toast.LENGTH_LONG).show();
	  }
}
