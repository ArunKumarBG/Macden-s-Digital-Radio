package com.arunkumarbgcbe.macdensdigitalradio.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalSharedStorage {

	public static final String TRACKID = "TrackId";
	public static final String TRACKTITLE = "TrackTitle";
	public static final String TRACKIMAGEURL = "TrackImageUrl";

	SharedPreferences preferences;
	private static final String MYPREFERENCES = "MacdensDigitalRadio";

	public LocalSharedStorage(Context ctx) {
		preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public void saveTrackId(String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(TRACKID, value);
		editor.commit();
	}

	public String getTrackId() {
		return preferences.getString(TRACKID, null);
	}

	public void saveTrackTitle(String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(TRACKTITLE, value);
		editor.commit();
	}

	public String getTrackTitle() {
		return preferences.getString(TRACKTITLE, null);
	}

	public void saveTrackImageUrl(String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(TRACKIMAGEURL, value);
		editor.commit();
	}

	public String getTrackImageUrl() {
		return preferences.getString(TRACKIMAGEURL, null);
	}

}
