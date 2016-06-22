package com.arunkumarbgcbe.macdensdigitalradio.util;


import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;




public class SubApplication extends /* MultiDex */Application {

	private static Typeface fontCondBold, fontCondLight;
	private static Typeface fontNarrowBold, fontNarrow;
	private static Typeface fontNarrowItalic;
	private static Typeface fontFvalmelo;
	private static Typeface fontdefaultbold;
	private static Typeface notification_font;
	private static Typeface revicons_font;
	
	String FONT_REVICONS = "FONT_REVICONS";

	String FONT_COND_BOLD = "FONT_COND_BOLD";
	String FONT_COND_LIGHT = "FONT_COND_LIGHT";

	String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";
	String FONT_NARROW = "FONT_NARROW";

	String FONT_FVALMELO = "FONT_FVALMELO";
	String FONT_DEFAULT_BOLD = "FONT_DEFAULT_BOLD";

	String NOTIFICATION_FONT = "NOTIFICATION_FONT";
	

	String carrier;
	String appVersion;
	String devInfo;
	String destination;

	static int availBalServer = 0;

	String defaultValue = "UnSpecified";
	String defaultGuesstLocValue = "CheckedOutasGuest";
	String devPhoneOwnerName;
	String devPhoneNumber;
	String devPhoneEmail;
	public ArrayList<String> selectedCodes;

	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	

	/**
	 * Check if service is running or not
	 * @param serviceName
	 * @param context
	 * @return
	 */
	public static boolean isServiceRunning(String serviceName, Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if(serviceName.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * @Override protected void attachBaseContext(Context base) { // TODO
	 * Auto-generated method stub super.attachBaseContext(base);
	 * MultiDex.install(this); }
	 */

	public void setTypeface(TextView textView, String typeface, Context ctx) {
		if (textView != null) {
			if (typeface.equals("FONT_COND_BOLD")) {
				textView.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals("FONT_COND_LIGHT")) {
				textView.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				textView.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				textView.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(NOTIFICATION_FONT)) {
				textView.setTypeface(getNotificationFont(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				textView.setTypeface(getfontFvAlmelo(ctx));
			} else if (typeface.equals(FONT_DEFAULT_BOLD)) {
				textView.setTypeface(Typeface.DEFAULT_BOLD);
			}

		}
	}

	/*public void setTypeface(TextInputLayout textInputLayout, String typeface, Context ctx) {
		if (textInputLayout != null) {
			if (typeface.equals("FONT_COND_BOLD")) {
				textInputLayout.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals("FONT_COND_LIGHT")) {
				textInputLayout.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				textInputLayout.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				textInputLayout.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(NOTIFICATION_FONT)) {
				textInputLayout.setTypeface(getNotificationFont(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				textInputLayout.setTypeface(getfontFvAlmelo(ctx));
			} else if (typeface.equals(FONT_DEFAULT_BOLD)) {
				textInputLayout.setTypeface(Typeface.DEFAULT_BOLD);
			}

		}
	}*/

	public void setTypeface(AutoCompleteTextView autoCompleteTextView, String typeface, Context ctx) {
		if (autoCompleteTextView != null) {
			if (typeface.equals(FONT_COND_BOLD)) {
				autoCompleteTextView.setTypeface(getfontCondBold(ctx));
			} else if (typeface.equals(FONT_COND_LIGHT)) {
				autoCompleteTextView.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				autoCompleteTextView.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				autoCompleteTextView.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				autoCompleteTextView.setTypeface(getfontFvAlmelo(ctx));
			} else if (typeface.equals(FONT_REVICONS)) {
				autoCompleteTextView.setTypeface(getReviconsFont(ctx));
			}

		}
	}

	public void setTypeface(Button button, String typeface, Context ctx) {
		if (button != null) {
			if (typeface.equals(FONT_COND_BOLD)) {
				button.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals(FONT_COND_LIGHT)) {
				button.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				button.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				button.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				button.setTypeface(getfontFvAlmelo(ctx));
			}else if (typeface.equals(FONT_REVICONS)) {
				button.setTypeface(getReviconsFont(ctx));
			}

		}
	}

	public void setTypeface(CheckBox checkBox, String typeface, Context ctx) {
		if (checkBox != null) {
			if (typeface.equals(FONT_COND_BOLD)) {
				checkBox.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals(FONT_COND_LIGHT)) {
				checkBox.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				checkBox.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				checkBox.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				checkBox.setTypeface(getfontFvAlmelo(ctx));
			}else if (typeface.equals(FONT_REVICONS)) {
				checkBox.setTypeface(getReviconsFont(ctx));
			}

		}
	}

	public void setTypeface(RadioButton radioButton, String typeface, Context ctx) {
		if (radioButton != null) {
			if (typeface.equals(FONT_COND_BOLD)) {
				radioButton.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals(FONT_COND_LIGHT)) {
				radioButton.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				radioButton.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				radioButton.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				radioButton.setTypeface(getfontFvAlmelo(ctx));
			} else if (typeface.equals(FONT_REVICONS)) {
				radioButton.setTypeface(getReviconsFont(ctx));
			}

		}
	}

	public void setTypeface(EditText editText, String typeface, Context ctx) {
		if (editText != null) {
			if (typeface.equals(FONT_COND_BOLD)) {
				editText.setTypeface(getfontCondBold(ctx));
			}

			else if (typeface.equals(FONT_COND_LIGHT)) {
				editText.setTypeface(getfontCondLight(ctx));
			} else if (typeface.equals(FONT_NARROW_BOLD)) {
				editText.setTypeface(getfontNarrowBold(ctx));
			} else if (typeface.equals(FONT_NARROW)) {
				editText.setTypeface(getfontNarrow(ctx));
			} else if (typeface.equals(FONT_FVALMELO)) {
				editText.setTypeface(getfontFvAlmelo(ctx));
			} else if (typeface.equals(FONT_REVICONS)) {
				editText.setTypeface(getReviconsFont(ctx));
			}

		}
	}

	public Typeface getfontCondLight(Context ctx) {
		if (fontCondLight == null) {
			fontCondLight = Typeface.createFromAsset(ctx.getAssets(), "font/OpenSans-CondLight.ttf");
		}
		return this.fontCondLight;
	}

	private Typeface getfontCondBold(Context ctx) {
		if (fontCondBold == null) {
			fontCondBold = Typeface.createFromAsset(ctx.getAssets(), "font/OpenSans-CondBold.ttf");
		}
		return this.fontCondBold;
	}

	public Typeface getfontNarrowBold(Context ctx) {
		if (fontNarrowBold == null) {
			fontNarrowBold = Typeface.createFromAsset(ctx.getAssets(), "font/verdanab.ttf");
		}
		return this.fontNarrowBold;
	}

	public Typeface getfontFvAlmelo(Context ctx) {
		if (fontFvalmelo == null) {
			fontFvalmelo = Typeface.createFromAsset(ctx.getAssets(), "font/fv_almelo.ttf");
		}
		return this.fontFvalmelo;
	}

	public Typeface getfontBold(Context ctx) {
		if (fontdefaultbold == null) {
			fontdefaultbold = Typeface.defaultFromStyle(Typeface.BOLD);
		}
		return this.fontdefaultbold;
	}

	public Typeface getfontNarrow(Context ctx) {
		if (fontNarrow == null) {
			fontNarrow = Typeface.createFromAsset(ctx.getAssets(), "font/verdana.ttf");
		}
		return this.fontNarrow;
	}

	public Typeface getNotificationFont(Context ctx) {
		if (notification_font == null) {
			notification_font = Typeface.createFromAsset(ctx.getAssets(), "font/Arsenal-Regular.otf");
		}
		return this.notification_font;
	}
	
	public Typeface getReviconsFont(Context ctx) {
		if (revicons_font == null) {
			revicons_font = Typeface.createFromAsset(ctx.getAssets(), "font/revicons.ttf");
		}
		return this.revicons_font;
	}

	public String getCarrier() {
		return ((carrier != null && carrier.length() > 0) ? carrier : defaultValue);
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getAppVersion() {
		return ((appVersion != null && appVersion.length() > 0) ? appVersion : defaultValue);
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDevInfo() {
		return ((devInfo != null && devInfo.length() > 0) ? devInfo : defaultValue);
	}

	public void setDevInfo(String devInfo) {
		this.devInfo = devInfo;
	}

	public String getDestination() {
		return ((destination != null && destination.length() > 0) ? destination : defaultValue);
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultGuesstLocValue() {
		return defaultGuesstLocValue;
	}

	public void setDefaultGuesstLocValue(String defaultGuesstLocValue) {
		this.defaultGuesstLocValue = defaultGuesstLocValue;
	}

	public int getAvailBalServer() {
		return availBalServer;
	}

	public void setAvailBalServer(int mAvailBalServer) {
		availBalServer = mAvailBalServer;
	}
	/*
	 * public String getDevPhoneOwnerName() { return ((devPhoneOwnerName != null
	 * && devPhoneOwnerName.length() > 0) ? devPhoneOwnerName : defaultValue); }
	 * 
	 * public void setDevPhoneOwnerName(String devPhoneOwnerName) {
	 * this.devPhoneOwnerName = devPhoneOwnerName; }
	 * 
	 * public String getDevPhoneNumber() { return ((devPhoneNumber != null &&
	 * devPhoneNumber.length() > 0) ? devPhoneNumber : defaultValue); }
	 * 
	 * public void setDevPhoneNumber(String devPhoneNumber) {
	 * this.devPhoneNumber = devPhoneNumber; }
	 * 
	 * public String getDevPhoneEmail() { return ((devPhoneEmail != null &&
	 * devPhoneEmail.length() > 0) ? devPhoneEmail : defaultValue); }
	 * 
	 * public void setDevPhoneEmail(String devPhoneEmail) { this.devPhoneEmail =
	 * devPhoneEmail; }
	 */

	public SpannableStringBuilder getSpannableMessage(Context ctx, String fontType, String key) {
		// TODO Auto-generated method stub
		SpannableStringBuilder spannableString = new SpannableStringBuilder(key);
		Typeface font;
		if (fontType.equals(FONT_COND_BOLD)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontCondBold(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		} else if (fontType.equals(FONT_COND_LIGHT)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontCondLight(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		} else if (fontType.equals(FONT_NARROW_BOLD)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontNarrowBold(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		} else if (fontType.equals(FONT_NARROW)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontNarrow(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		} else if (fontType.equals(FONT_FVALMELO)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontFvAlmelo(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		} else if (fontType.equals(FONT_DEFAULT_BOLD)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getfontBold(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}else if (fontType.equals(FONT_REVICONS)) {
			spannableString.setSpan(new CustomTypefaceSpan("", getReviconsFont(ctx)), 0, key.length(),
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		return spannableString;
	}

	/*
	 * public SpannableStringBuilder getSpannableMessage(Context ctx, String
	 * fontType, String key,int fontSize) { // TODO Auto-generated method stub
	 * SpannableStringBuilder spannableString = new SpannableStringBuilder(key);
	 * Typeface font; if (fontType.equals(FONT_COND_BOLD)) {
	 * spannableString.setSpan(new AbsoluteSizeSpan(fontSize),
	 * getfontCondBold(ctx), 0, key.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
	 * } else if (fontType.equals(FONT_COND_LIGHT)) {
	 * spannableString.setSpan(new CustomTypefaceSpan("",
	 * getfontCondLight(ctx)), 0, key.length(),
	 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE); } else if
	 * (fontType.equals(FONT_NARROW_BOLD)) { spannableString.setSpan(new
	 * CustomTypefaceSpan("", getfontNarrowBold(ctx)), 0, key.length(),
	 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE); } else if
	 * (fontType.equals(FONT_NARROW)) { spannableString.setSpan(new
	 * CustomTypefaceSpan("", getfontNarrow(ctx)), 0, key.length(),
	 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE); } else if
	 * (fontType.equals(FONT_FVALMELO)) { spannableString.setSpan(new
	 * CustomTypefaceSpan("", getfontFvAlmelo(ctx)), 0, key.length(),
	 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE); } return spannableString; }
	 */

}