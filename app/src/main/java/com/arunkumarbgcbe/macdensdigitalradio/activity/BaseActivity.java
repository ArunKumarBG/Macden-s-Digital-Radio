package com.arunkumarbgcbe.macdensdigitalradio.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.arunkumarbgcbe.macdensdigitalradio.util.SubApplication;

public class BaseActivity extends ActionBarActivity {
	SubApplication subApp;
	protected Activity mContext;
	Activity activity;

	String TagScreen;
	static Context context;
	static String appVersion = "4.2";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = this;
		mContext = this;
		subApp = new SubApplication();
		context = this;
	}

	public void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void onPause() {

		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void onNewIntent(Intent intent) {
		/** Needed for Localytics, Do not delete **/
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}