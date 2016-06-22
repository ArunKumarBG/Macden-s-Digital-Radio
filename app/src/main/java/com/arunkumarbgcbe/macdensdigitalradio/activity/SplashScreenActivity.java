package com.arunkumarbgcbe.macdensdigitalradio.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
//import android.util.Log;
import android.view.Window;

import com.arunkumarbgcbe.macdensdigitalradio.R;


public class SplashScreenActivity extends Activity {
	Context mContext;

	String reviewResponse = null;
	boolean timerStatus = false;
	boolean reviewcalled = false;

	boolean needToLoadReview = false;
	boolean landingCalled = false;

	Activity activity;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (!isTaskRoot()) {
			finish();
			return;
		}
		setContentView(R.layout.activity_splash);
		activity = this;

		/*int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {

			Window window = activity.getWindow();

			// clear FLAG_TRANSLUCENT_STATUS flag:
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

			// finally change the color
			window.setStatusBarColor(getResources().getColor(R.color.dim_grey));
		} else {
			// do something for phones running an SDK before lollipop
		}*/

		mContext = this;

		loadScreen();

	}

	void loadScreen() {
		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				timerStatus = true;
			}

			@Override
			public void onFinish() {
				timerStatus = false;
				callLandingActivity();
			}
		}.start();
	}

	String getCurrentTime() {
		String timeStamp = new SimpleDateFormat("HH-mm-ss", Locale.ENGLISH).format(Calendar.getInstance().getTime());
		return timeStamp;
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
		System.exit(0);
	}

	@Override
	protected void onStart() {
		super.onStart();
		try {
			Log.i("INFO", "onStart()");

		} catch (Exception e) {
			Log.i("INFO", e.getMessage());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
	}

	public void callLandingActivity() {
		if (!landingCalled) {
			Intent intent = new Intent(mContext, HomeActivity.class);
			startActivity(intent);
			landingCalled = true;
			finish();
		}
	}

}
