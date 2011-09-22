package com.zenika.signature.db;


public interface ITable {
	public static final String PROVIDER_AUTHORITIES = "com.zenika.signature.android.provider";
	public static final String URI_PREFIX			= "content://";
	public static final String BASE_URI				= URI_PREFIX + PROVIDER_AUTHORITIES;
	public static final String CURSOR_DIR			= "vnd.android.cursor.dir";
	public static final String CURSOR_ITEM			= "vnd.android.cursor.item";
	public static final String VND_PREFIX			= "vnd.com.zenika";
}
