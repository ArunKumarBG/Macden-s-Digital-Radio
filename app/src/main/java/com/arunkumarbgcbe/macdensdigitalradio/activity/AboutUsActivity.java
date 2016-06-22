package com.arunkumarbgcbe.macdensdigitalradio.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.util.SubApplication;

public class AboutUsActivity extends BaseActivity {

	SubApplication application;

	String NOTIFICATION_FONT = "NOTIFICATION_FONT";
	String FONT_REVICONS = "FONT_REVICONS";
	String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";
	/*String FONT_NARROW = "FONT_NARROW";
	String FONT_DEFAULT_BOLD = "FONT_DEFAULT_BOLD";*/
	String FONT_SANS_BOLD = "FONT_SANS_BOLD";
	String FONT_SANS = "FONT_SANS";
	
	TextView textView_version,textView_aboutus;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		application = new SubApplication();

		SpannableStringBuilder spannableString = application.getSpannableMessage(getApplicationContext(), FONT_REVICONS,getResources().getString(R.string.action_aboutus));
		ab.setTitle(spannableString);

		/*int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){

			Window window = activity.getWindow();

			// clear FLAG_TRANSLUCENT_STATUS flag:
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

			// finally change the color
			window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
		} else{
			// do something for phones running an SDK before lollipop
		}*/
		
		initializeWidget();


		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String version = pInfo.versionName;
		
		textView_version.setText(getString(R.string.app_version) + version);		
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			
	}
	
	void initializeWidget() {
		
		textView_version = (TextView)findViewById(R.id.textView_version);
		application.setTypeface(textView_version, FONT_REVICONS, getApplicationContext());
		
		textView_aboutus = (TextView)findViewById(R.id.textView_aboutus);
		application.setTypeface(textView_aboutus, FONT_REVICONS, getApplicationContext());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case android.R.id.home:
			actionBackPressed();
			break;

		}
		return true;
	}

	@Override
	public void onBackPressed() {
		actionBackPressed();
	}

	void actionBackPressed() {
		super.onBackPressed();
	}

}
