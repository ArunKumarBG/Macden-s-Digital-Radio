package com.arunkumarbgcbe.macdensdigitalradio.util;

import com.arunkumarbgcbe.macdensdigitalradio.R;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog {
	Context ctx;
	String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";
	String FONT_NARROW = "FONT_NARROW";

	String FONT_CAPTION_BOLD = "FONT_CAPTION_BOLD";
	String FONT_CAPTION = "FONT_CAPTION";

	String FONT_SANS_BOLD = "FONT_SANS_BOLD";
	String FONT_SANS = "FONT_SANS";
	SubApplication application;

	public CustomDialog(Context ctx) {
		this.ctx = ctx;
		application = new SubApplication();
	}

	public void showCustomDialog(String msg) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
		SpannableStringBuilder spannableString = application.getSpannableMessage(ctx, FONT_NARROW, msg);
		builder1.setMessage(spannableString);
		builder1.setCancelable(true);

		builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog alert11 = builder1.create();
		alert11.show();

		TextView textView = (TextView) alert11.findViewById(android.R.id.message);
		application.setTypeface(textView, FONT_NARROW, ctx);
		textView.setTextSize(20);

		Button positive_button = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
		positive_button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		positive_button.setTextColor(ctx.getResources().getColor(R.color.dialog_pressed_true_grey));
		application.setTypeface(positive_button, FONT_NARROW, ctx);

	}

}
