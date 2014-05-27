package com.zenika.signature.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class Signature implements BaseColumns, ITable{

	public static final String 	DATABASE_TABLE 			= "signature";
	public static final String 	CONTENT_STRING			= BASE_URI + "/" + DATABASE_TABLE;
	public static final Uri  	CONTENT_URI 			= Uri.parse(CONTENT_STRING);
	public static final String	DEFAULT_SORT_ORDER 		= _ID + " DESC";
	
	public static final String	TYPES					= CURSOR_DIR + "/" + VND_PREFIX + "." + DATABASE_TABLE;
	public static final String	TYPE					= CURSOR_ITEM + "/" + VND_PREFIX + "." + DATABASE_TABLE;
	
	// columns
	public static final String SIGNATURE = "_signature";
	public static final String DATE		 = "_date";
	
	// indexes
	public static final int ID_INDEX 			= 0;
	public static final int SIGNATURE_INDEX		= 1;
	public static final int DATE_INDEX			= 2;
	
	public static final String[] PROJECTION = new String[]{_ID, SIGNATURE, DATE};
	
	/**
     * The content:// style URL for a given row, identified by its id.
     *
     * @param id The row id.
     *
     * @return The unique content URL for the specified row.
     */
    public static Uri getContentUri(long id) 
    {
        return Uri.parse(CONTENT_URI +"/" + id);
    }
    
	public static final String DATABASE_CREATE =  "create table IF NOT EXISTS " + DATABASE_TABLE 
				+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ SIGNATURE + " BLOB, "
				+ DATE + " VARCHAR"
				+ ");";

}
