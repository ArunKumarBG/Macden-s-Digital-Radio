package com.arunkumarbgcbe.macdensdigitalradio.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.controls.Controls;
import com.arunkumarbgcbe.macdensdigitalradio.network.ConnectionDetector;
import com.arunkumarbgcbe.macdensdigitalradio.service.SongService;
import com.arunkumarbgcbe.macdensdigitalradio.util.AppController;
import com.arunkumarbgcbe.macdensdigitalradio.util.LocalSharedStorage;
import com.arunkumarbgcbe.macdensdigitalradio.util.PlayerConstants;
import com.arunkumarbgcbe.macdensdigitalradio.util.SubApplication;
import com.arunkumarbgcbe.macdensdigitalradio.util.UtilFunctions;

import java.net.URL;


public class AudioPlayerActivity extends BaseActivity {

	//Button btnBack;
	//static Button btnPause;
	//Button btnNext;
	//static Button btnPlay;
	SubApplication application;

	String FONT_REVICONS = "FONT_REVICONS";
	String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";

	String FONT_SANS_BOLD = "FONT_SANS_BOLD";
	String FONT_SANS = "FONT_SANS";

	static TextView textNowPlaying;
	static TextView textAlbumArtist;
	static TextView textComposer;
	static LinearLayout linearLayoutPlayer;
	ProgressBar progressBar;
	static Context context;
	TextView textBufferDuration, textDuration;

	String mTitle = "Track List";

	ConnectionDetector connDetector;

	LocalSharedStorage sharedPref;

	NetworkImageView imageAlbumArt;

	ImageView iv_player_control;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().hide();
		setContentView(R.layout.audio_player);

		application = new SubApplication();

		context = this;
		init();

		Intent intent = getIntent();
		mTitle = intent.getStringExtra(getString(R.string.key_title));

		ImageLoader mImageLoader;
		if(sharedPref.getTrackImageUrl()!=null)
		{
			mImageLoader = AppController.getInstance().getImageLoader();
			mImageLoader.get(sharedPref.getTrackImageUrl(), ImageLoader.getImageListener(imageAlbumArt,
					R.drawable.ic_library_music_gray, R.drawable.ic_library_music_gray));
			imageAlbumArt.setImageUrl(sharedPref.getTrackImageUrl(), mImageLoader);
		}

		if (sharedPref.getTrackTitle() != null) {
			textNowPlaying.setText(sharedPref.getTrackTitle());

		} else {
			if(mTitle!=null) {
				textNowPlaying.setText(mTitle);
			}
		}

		if(mTitle!=null)
		{
			textAlbumArtist.setText(mTitle);
		}

		iv_player_control.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (connDetector.checkInternetConnection()) {
					resetplayer();
				}else {
					showCustomToast(getResources().getString(R.string.network_error));
					finish();
				}
			}

		});


		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private void init() {
		getViews();
		setListeners();

		connDetector = new ConnectionDetector(this);
		sharedPref = new LocalSharedStorage(this);

		/*progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), Mode.SRC_IN);
		PlayerConstants.PROGRESSBAR_HANDLER = new Handler(){
			 @Override
		        public void handleMessage(Message msg){
				 Integer i[] = (Integer[])msg.obj;
				 textBufferDuration.setText(UtilFunctions.getDuration(i[0]));
				 textDuration.setText(UtilFunctions.getDuration(i[1]));
				 progressBar.setProgress(i[2]);
		    	}
		};*/
	}

	private void setListeners() {
		/*btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.previousControl(getApplicationContext());
			}
		});*/

		/*btnPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.pauseControl(getApplicationContext());
			}
		});

		btnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Controls.playControl(getApplicationContext());
			}
		});*/

		/*btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.nextControl(getApplicationContext());
			}
		});*/
	}

	public static void changeUI(){
		updateUI();
		//changeButton();
	}

	private void getViews() {
		//btnBack = (Button) findViewById(R.id.btnBack);
		//btnPause = (Button) findViewById(R.id.btnPause);
		//btnNext = (Button) findViewById(R.id.btnNext);
		//btnPlay = (Button) findViewById(R.id.btnPlay);
		textNowPlaying = (TextView) findViewById(R.id.textNowPlaying);
		linearLayoutPlayer = (LinearLayout) findViewById(R.id.linearLayoutPlayer);
		textAlbumArtist = (TextView) findViewById(R.id.textAlbumArtist);
		textComposer = (TextView) findViewById(R.id.textComposer);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
		textDuration = (TextView) findViewById(R.id.textDuration);
		textNowPlaying.setSelected(true);
		textAlbumArtist.setSelected(true);

		iv_player_control = (ImageView) findViewById(R.id.iv_player_control);

		imageAlbumArt =(NetworkImageView)findViewById(R.id.imageAlbumArt);
	}

	@Override
	public void onResume() {
		super.onResume();
		/*boolean isServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
		if (isServiceRunning) {
			updateUI();
		}
		changeButton();*/
	}

	/*public static void changeButton() {
		if(PlayerConstants.SONG_PAUSED){
			btnPause.setVisibility(View.GONE);
			btnPlay.setVisibility(View.VISIBLE);
		}else{
			btnPause.setVisibility(View.VISIBLE);
			btnPlay.setVisibility(View.GONE);
		}
	}*/

	private static void updateUI() {
		try{
			String songName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getTitle();
			/*String artist = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getArtist();*/
			String album = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getGenre();
		/*	String composer = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getComposer();*/
			textNowPlaying.setText(songName);
		/*	textAlbumArtist.setText(artist + " - " + album);*/
			/*if(composer != null && composer.length() > 0){
				textComposer.setVisibility(View.VISIBLE);
				textComposer.setText(composer);
			}else{
				textComposer.setVisibility(View.GONE);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			long albumId = Integer.parseInt(PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getId());
			LocalSharedStorage sharedPref = new LocalSharedStorage(context);
			Bitmap albumArt = null;
			try {
				URL albumUrl = new URL(sharedPref.getTrackImageUrl());
				albumArt = BitmapFactory.decodeStream(albumUrl.openConnection().getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Bitmap albumArt = UtilFunctions.getAlbumart(context, albumId);
			if(albumArt != null){
				linearLayoutPlayer.setBackgroundDrawable(new BitmapDrawable(albumArt));
			}else{
				linearLayoutPlayer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_audiotrack_gray));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void resetplayer() {
		// TODO Auto-generated method stub
		if (HomeActivity.musicSrv != null) {
			if (HomeActivity.musicSrv.isPng()) {
				HomeActivity.musicSrv.pausePlayer();
				iv_player_control.setImageResource(R.drawable.ic_play_arrow_black);
			} else {
				HomeActivity.musicSrv.go();
				iv_player_control.setImageResource(R.drawable.ic_pause_black);
			}
			if (sharedPref.getTrackTitle() != null) {
				textNowPlaying.setText(sharedPref.getTrackTitle());
			}
			ImageLoader mImageLoader;
			if (sharedPref.getTrackImageUrl() != null) {
				mImageLoader = AppController.getInstance().getImageLoader();
				mImageLoader.get(sharedPref.getTrackImageUrl(), ImageLoader.getImageListener(imageAlbumArt,
						R.drawable.ic_audiotrack_black, R.drawable.ic_audiotrack_black));
				imageAlbumArt.setImageUrl(sharedPref.getTrackImageUrl(), mImageLoader);
			}
			/*rl_musiccontorl.setVisibility(View.VISIBLE);
			card_view_musiccontorl.setVisibility(View.VISIBLE);*/

		} else {
			/*rl_musiccontorl.setVisibility(View.GONE);
			card_view_musiccontorl.setVisibility(View.GONE);*/
		}

	}

	public void showCustomToast(String msg) {
		SpannableStringBuilder spannableString = application.getSpannableMessage(mContext, FONT_REVICONS,
				msg);
		Toast.makeText(mContext, spannableString, Toast.LENGTH_SHORT).show();
	}
}
