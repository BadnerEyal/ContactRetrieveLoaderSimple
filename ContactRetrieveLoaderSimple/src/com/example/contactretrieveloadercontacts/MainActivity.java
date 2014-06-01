package com.example.contactretrieveloadercontacts;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.annotation.SuppressLint;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * ContentProvider
 * http://androidexample.com/Content_Provider_Basic/index.php?view=article_discription&aid=120&aaid=140
 * 
 * LoaderManager
 *
 */
@SuppressLint("NewApi")
public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	private final static String TAG = MainActivity.class.getSimpleName();//  <==> "MainActivity";

	private CursorAdapter adapter;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getLoaderManager().initLoader(0, null, this); 
        
        setListAdapter(adapter);
    }

    
    
    //יצרת הלידר
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) 
	{
		
		
		//ContentProvider קבלת אנשי קשר במכשיר
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
	        
	        Log.d(TAG, uri.toString());
	        
	        String[] projection = new String[] { ContactsContract.Contacts._ID,
	            ContactsContract.Contacts.DISPLAY_NAME };
	        
//	        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
//	            + ("1") + "'";
//	        
	        
	        String[] selectionArgs = null;
	        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
	            + " COLLATE LOCALIZED ASC";
		
		
		CursorLoader cursorLoader = 
				new CursorLoader(this,uri, projection, null, null, null);
		
		return cursorLoader;
	}

	//כאשר גמרנו 
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		//adapter יצרת המתאם 
	    adapter = new CursorAdapter(this, cursor, true)
        {

			@Override
			public void bindView(View view, Context arg1, Cursor cursor) 
			{
				TextView textView = (TextView)view;
				String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				textView.setText(displayName);
			}

			@Override
			public View newView(Context arg0, Cursor arg1, ViewGroup arg2) 
			{
				return new TextView(MainActivity.this);
			}
        	
        };

        //חיבור המתאם לרשימה
        getListView().setAdapter(adapter);
        //רענון הרשימה
        //במקרה שנוריד איש קשר או נוסיף יתרענן מלבד
        adapter.swapCursor(cursor);
		
		
	}

	//במקרה שלא הצלחנו 
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
		
	}

}








