package com.zenika.signature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenika.R;
import com.zenika.signature.db.Signature;
import com.zenika.signature.signature.SignatureActivity;

public class MainActivity extends Activity 
{
	
	private QueryTask mTask;
	
	private TextView 	mDateTV;
	private ImageView	mSignatureIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		mDateTV = (TextView) findViewById(R.id.main_contract_date_text);
		mSignatureIV = (ImageView) findViewById(R.id.main_signature_bitmap);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		mTask = new QueryTask(getApplicationContext());
		mTask.execute();
	}
	
	@Override
	protected void onPause() 
	{
		if(mTask != null)
		{
			mTask.cancel(true);
			mTask = null;
		}
		super.onPause();
	}
	
	public void getSignature(View view)
	{
		startActivity(new Intent(this, SignatureActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Reset DB");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		mSignatureIV.setImageBitmap(null);
		getContentResolver().delete(Signature.CONTENT_URI, null, null);
		return true;
	}
	
	private class QueryTask extends AsyncTask<Void, Void, Cursor>
	{
		
		private Context context;
		
		public QueryTask(Context context) 
		{
			this.context = context;
		}

		@Override
		protected Cursor doInBackground(Void... arg0) 
		{
			return context.getContentResolver().query(
					Signature.CONTENT_URI, 
					Signature.PROJECTION, 
					null, 
					null, 
					Signature.DEFAULT_SORT_ORDER);
		}
		
		@Override
		protected void onPostExecute(Cursor result) 
		{
			if(result != null && result.moveToFirst())
			{
				byte[] rawPict = result.getBlob(Signature.SIGNATURE_INDEX);
				Bitmap b = BitmapFactory.decodeByteArray(rawPict, 0, rawPict.length);
				mSignatureIV.setImageBitmap(b);
				mDateTV.setText(result.getString(Signature.DATE_INDEX));
			}
		}
		
	}
}
