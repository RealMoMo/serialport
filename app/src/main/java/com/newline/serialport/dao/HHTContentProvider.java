package com.newline.serialport.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class HHTContentProvider {
	//ContentProvider uri
	public static final String SETTING_CONTENT_PROVIDER_URI="content://mstar.hv.usersetting/functionsetting";
	protected static final String LAST_KNOW_SYSTEM_FLAG = "last_know_system_flag";
	public static final String FAVORITE_SOURCE_NAME = "strFavoriteSource";
	public static final String ENABLE_SCREEN_LOCK= "bEnableScreenLock";
	public static final String DEFAULT_INPUT_SOURCE_TYPE_NAME = "nDefaultInputSourceType";
	protected static final String B_ENABLE_SCREEN_LOCK = "bEnableScreenLock";
	protected static final String FILED_CLEAR_WHITEBROAD_SCREENSHOT = "nCleanFilesPeriod";

	protected static final String CUR_ECO_MODE = "curECOMode";
	protected static final String FIELD_LEFT_FLOATBAR_SHOW_HIDE = "bEnableLeftFloatBar";
	protected static final String FIELD_RIGHT_FLOATBAR_SHOW_HIDE = "bEnableRightFloatBar";
	protected static final String SOURCE_DISPLAY_RATIO_SETTING_CONTENT_PROVIDER_URI = "content://mstar.tv.usersetting/videosetting";
	protected static final String FIELD_E_PICTURE = "ePicture";
	protected static final String B_NEW_INPUT_SOURCE = "bNewInpputSource";


	public static int doUpdate(Context context, String uriAddress, String indexName, int value) {
		Uri uri = Uri.parse(uriAddress);
		ContentResolver resolver = context.getContentResolver();
		ContentValues conValue = new ContentValues();
		conValue.put(indexName, value);
		try {
			int count = resolver.update(uri, conValue, null, null);
			Log.d("HHTContentProvider ", "doUpdate success count =========:" + count);
			return count;
		} catch (Exception exception) {
			Log.d("HHTContentProvider ", "doUpdate exception failed");
			return 0;
		}

	}

	public static int doUpdateString(Context context, String uriAddress, String indexName, String value) {
		Uri uri = Uri.parse(uriAddress);
		ContentResolver resolver = context.getContentResolver();
		ContentValues conValue = new ContentValues();
		conValue.put(indexName, value);
		try {
			int count = resolver.update(uri, conValue, null, null);
			Log.d("HHTContentProvider ", "doUpdate success count =========:" + count);
			return count;
		} catch (Exception exception) {
			Log.d("HHTContentProvider ", "doUpdate exception failed");
			return 0;
		}

	}

	public static int doQuery(Context context, String uriAddress, String indexName) {
		Uri uri = Uri.parse(uriAddress);
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);

		int value = 0;
		cursor.moveToFirst();
		value = cursor.getInt(cursor.getColumnIndex(indexName));
		cursor.close();
		return value;
	}

	public static String doQueryString(Context context, String uriAddress, String indexName) {
		Uri uri = Uri.parse(uriAddress);
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);

		String value = "";
		cursor.moveToFirst();
		value = cursor.getString(cursor.getColumnIndex(indexName));
		cursor.close();
		return value;
	}

}
