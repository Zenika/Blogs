package com.zenika.signature.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.zenika.signature.app.AppConfig;

public class ZenSignatureContentProvider extends ContentProvider{
	
	public static final String TAG = ZenSignatureContentProvider.class.getSimpleName() + " - ";

	private static final String DATABASE_NAME		= "bconnect.db";
	private static final int	DATABASE_VERSION	= 1;

	private static final int SIGNATURES = 1;
	private static final int SIGNATURE_ID = 2;	
	
	
	private static final UriMatcher uriMatcher;
	
	static
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(ITable.PROVIDER_AUTHORITIES, Signature.DATABASE_TABLE, SIGNATURES);
		uriMatcher.addURI(ITable.PROVIDER_AUTHORITIES, Signature.DATABASE_TABLE + "/#", SIGNATURE_ID);
	}

	private SQLiteDatabase bconnectDB;

	@Override
	public boolean onCreate() 
	{
		Log.d(AppConfig.TAG, TAG + "onCreate");
		OpenDatabaseHelper dbHelper = new OpenDatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
		bconnectDB = dbHelper.getWritableDatabase();
		return bconnectDB != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) 
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName(uri));
		// if this is a row query
		switch (uriMatcher.match(uri)) {
		case SIGNATURE_ID:
			qb.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
			break;
		default:
			break;
		}
		
		//if no sort, get default
		String orderBy;
		if(TextUtils.isEmpty(sortOrder))
		{
			orderBy = getDefaultOrder(uri);
		}
		else
		{
			orderBy = sortOrder;
		}
		
		Cursor c = qb.query(bconnectDB, projection, selection, selectionArgs, null, null, orderBy);
		
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{

		long rowId = bconnectDB.insertOrThrow(getTableName(uri), null, values);
		Uri newUri = uri;
		switch (uriMatcher.match(uri)) {
		case SIGNATURES:
		case SIGNATURE_ID:
			newUri = Signature.CONTENT_URI;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
		newUri = ContentUris.withAppendedId(newUri, rowId);
		getContext().getContentResolver().notifyChange(newUri, null);
		return newUri;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) 
	{
		int count;
		String id;
		switch (uriMatcher.match(uri)) {
		case SIGNATURES:
			count = bconnectDB.update(getTableName(uri), values, selection, selectionArgs);
			break;
		case SIGNATURE_ID:
			id = uri.getLastPathSegment();
			count = bconnectDB.update(getTableName(uri), values, 
					BaseColumns._ID + "=" +id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
		return count;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		int count;
		String id;
		switch (uriMatcher.match(uri)) {
		case SIGNATURES:
			count = bconnectDB.delete(getTableName(uri), selection, selectionArgs);
			break;
		case SIGNATURE_ID:
			id = uri.getLastPathSegment();
			count = bconnectDB.delete(getTableName(uri), 
					BaseColumns._ID + "=" +id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) 
	{
		switch (uriMatcher.match(uri)) {
		case SIGNATURES: return Signature.TYPES;
		case SIGNATURE_ID: return Signature.TYPE;

		default: throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
	}
	
	private String getTableName(Uri uri)
	{
		switch (uriMatcher.match(uri)) {
		case SIGNATURE_ID:
		case SIGNATURES: return Signature.DATABASE_TABLE;
		default:
			throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
	}
	
	private String getDefaultOrder(Uri uri)
	{
		switch (uriMatcher.match(uri)) {
		case SIGNATURES:
		case SIGNATURE_ID: return Signature.DEFAULT_SORT_ORDER;

		default: throw new IllegalArgumentException("Unsupported Uri: " + uri);
		}
	}
	
	private static class OpenDatabaseHelper extends SQLiteOpenHelper
	{
		
		private static final String TAG = OpenDatabaseHelper.class.getSimpleName() + " - ";

		public OpenDatabaseHelper(Context context, String name, CursorFactory factory, int version) 
		{
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			Log.d(AppConfig.TAG, TAG + "onCreate");
			// signatures
			db.execSQL(Signature.DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
		
	}

}
