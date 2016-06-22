package com.arunkumarbgcbe.macdensdigitalradio.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.data.Track;
import com.arunkumarbgcbe.macdensdigitalradio.data.Track.TrackDetails;
import com.arunkumarbgcbe.macdensdigitalradio.network.ConnectionDetector;
import com.arunkumarbgcbe.macdensdigitalradio.util.AppController;
import com.arunkumarbgcbe.macdensdigitalradio.util.CustomDialog;
import com.arunkumarbgcbe.macdensdigitalradio.util.LocalSharedStorage;
import com.arunkumarbgcbe.macdensdigitalradio.util.SubApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TrackListActivity extends BaseActivity {

	public static final String TAG = TrackListActivity.class.getName();

	SubApplication application;

	String FONT_REVICONS = "FONT_REVICONS";
	String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";

	String FONT_SANS_BOLD = "FONT_SANS_BOLD";
	String FONT_SANS = "FONT_SANS";

	LocalSharedStorage sharedPref;

	ConnectionDetector connDetector;
	CustomDialog customDialog;

	TextView textView_title, tv_track_title;

	RecyclerView listView_trackList;

	RelativeLayout rl_musiccontorl;
	
	CardView card_view_musiccontorl;

	ImageView  iv_player_control;

	NetworkImageView iv_track_image,main_image;

	ArrayList<TrackDetails> mArrAllTrackDetails, mArrTrackList;

	Track mObjTrack;

	String mTitle = "Track List";

	String mIcon= "Track List";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracklist);

		Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(toolbar);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		application = new SubApplication();

		initializeWidget();

		Intent intent = getIntent();
		mTitle = intent.getStringExtra(getString(R.string.key_title));
		mIcon = intent.getStringExtra(getString(R.string.key_artwork_url));
		mObjTrack = (Track) intent.getSerializableExtra(getString(R.string.key_download_url));

		if (mObjTrack != null) {
			mArrAllTrackDetails = mObjTrack.getTrackDetails();
		}

		ImageLoader mImageLoader;
		if(mIcon!=null)
		{
			mImageLoader = AppController.getInstance().getImageLoader();
			mImageLoader.get(mIcon, ImageLoader.getImageListener(main_image,
					R.drawable.ic_library_music_gray, R.drawable.ic_library_music_gray));
			main_image.setImageUrl(mIcon, mImageLoader);
		}

		SpannableStringBuilder spannableString = application.getSpannableMessage(getApplicationContext(), FONT_REVICONS,
				mTitle);
		ab.setTitle(spannableString);


		setUpTrackList();

		// setController();

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case android.R.id.home:
			actionBackPressed();
			break;
		/*
		 * case R.id.action_end: stopService(playIntent); musicSrv = null;
		 * System.exit(0); break;
		 * 
		 * case R.id.action_shuffle: musicSrv.setShuffle(); break;
		 */
		}

		return true;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (connDetector.checkInternetConnection()) {
			
		}else {
			showCustomToast(getString(R.string.network_error));
			finish();
		}
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		actionBackPressed();
	}

	void actionBackPressed() {
		super.onBackPressed();
	}

	private void initializeWidget() {
		// TODO Auto-generated method stub
		connDetector = new ConnectionDetector(this);
		customDialog = new CustomDialog(TrackListActivity.this);
		sharedPref = new LocalSharedStorage(this);

		textView_title = (TextView) findViewById(R.id.textView_title);
		application.setTypeface(textView_title, FONT_REVICONS, getApplicationContext());

		tv_track_title = (TextView) findViewById(R.id.tv_track_title);
		application.setTypeface(tv_track_title, FONT_REVICONS, getApplicationContext());

		listView_trackList = (RecyclerView) findViewById(R.id.listView_trackList);

		rl_musiccontorl = (RelativeLayout) findViewById(R.id.rl_musiccontorl);

		card_view_musiccontorl =(CardView)findViewById(R.id.card_view_musiccontorl);
		
		iv_player_control = (ImageView) findViewById(R.id.iv_player_control);

		iv_track_image = (NetworkImageView) findViewById(R.id.iv_track_image);

		main_image = (NetworkImageView) findViewById(R.id.main_image);

		mObjTrack = new Track();

		mArrAllTrackDetails = new ArrayList<TrackDetails>();

		mArrTrackList = new ArrayList<TrackDetails>();

	}

	private void setUpTrackList() {
		// TODO Auto-generated method stub

		if (mArrAllTrackDetails != null) {

			if (mArrAllTrackDetails.size() > 0) {

				mArrTrackList = new ArrayList<TrackDetails>();
				for (int i = 0; i < mArrAllTrackDetails.size(); i++) {
					if (mArrAllTrackDetails.get(i).getGenre().equalsIgnoreCase(mTitle)) {
						mArrTrackList.add(mArrAllTrackDetails.get(i));
					}
				}
				if (mArrTrackList != null) {
					if (mArrTrackList.size() == 1 || mArrTrackList.size() == 0) {
						textView_title.setText(mArrTrackList.size() + " " + getString(R.string.track));
					} else {
						textView_title.setText(mArrTrackList.size() + " " + getString(R.string.tracks));
					}
					displayMusicPlayerControl();

					if (mArrTrackList.size() > 0) {
						LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
						listView_trackList.setHasFixedSize(true);
						mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
						listView_trackList.setLayoutManager(mLayoutManager);
						listView_trackList.setItemAnimator(new DefaultItemAnimator());
						listView_trackList.setAdapter(new TrackListAdapter(mArrTrackList));
					}
				}

			}
		}

		card_view_musiccontorl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TrackListActivity.this,AudioPlayerActivity.class);
				intent.putExtra(getString(R.string.key_title), mTitle);
				intent.putExtra(getString(R.string.key_label_name), mTitle);
				intent.putExtra(getString(R.string.key_artwork_url), mIcon);
				startActivity(intent);
			}
		});

	}

	private void displayMusicPlayerControl() {
		// TODO Auto-generated method stub
		if (HomeActivity.musicSrv != null) {
			rl_musiccontorl.setVisibility(View.VISIBLE);
			card_view_musiccontorl.setVisibility(View.VISIBLE);
			if (sharedPref.getTrackTitle() != null) {
				tv_track_title.setText(sharedPref.getTrackTitle());
			} else {
				rl_musiccontorl.setVisibility(View.GONE);
				card_view_musiccontorl.setVisibility(View.GONE);
			}
			ImageLoader mImageLoader;
			if (sharedPref.getTrackImageUrl() != null) {
				mImageLoader = AppController.getInstance().getImageLoader();
				mImageLoader.get(sharedPref.getTrackImageUrl(), ImageLoader.getImageListener(iv_track_image,
						R.drawable.ic_audiotrack_black, R.drawable.ic_audiotrack_black));
				iv_track_image.setImageUrl(sharedPref.getTrackImageUrl(), mImageLoader);
			}
			if (HomeActivity.musicSrv.isPng()) {
				iv_player_control.setImageResource(R.drawable.ic_pause_black);
			} else {
				iv_player_control.setImageResource(R.drawable.ic_play_arrow_black);
			}

		} else {
			rl_musiccontorl.setVisibility(View.GONE);
			card_view_musiccontorl.setVisibility(View.GONE);
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
				tv_track_title.setText(sharedPref.getTrackTitle());
			}
			ImageLoader mImageLoader;
			if (sharedPref.getTrackImageUrl() != null) {
				mImageLoader = AppController.getInstance().getImageLoader();
				mImageLoader.get(sharedPref.getTrackImageUrl(), ImageLoader.getImageListener(iv_track_image,
						R.drawable.ic_audiotrack_black, R.drawable.ic_audiotrack_black));
				iv_track_image.setImageUrl(sharedPref.getTrackImageUrl(), mImageLoader);
			}
			rl_musiccontorl.setVisibility(View.VISIBLE);
			card_view_musiccontorl.setVisibility(View.VISIBLE);

		} else {
			rl_musiccontorl.setVisibility(View.GONE);
			card_view_musiccontorl.setVisibility(View.GONE);

		}

	}


	@Override
	protected void onStart() {
		super.onStart();

	}

	private void showMessage(final String msg) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!isFinishing()) {
					customDialog.showCustomDialog(msg);
				}
			}
		});
	}
	
	public void showCustomToast(String msg) {		
		SpannableStringBuilder spannableString = application.getSpannableMessage(mContext, FONT_REVICONS,
				msg);
		Toast.makeText(mContext, spannableString, Toast.LENGTH_SHORT).show();		
	}

	public class TrackListAdapter
			extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

		private final ArrayList<TrackDetails> arrTracklist;
		private int pos;
		public TrackListAdapter(ArrayList<TrackDetails> items) {
			arrTracklist = items;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.tracklist_listitem, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {

			if (arrTracklist != null) {
				holder.mItem = arrTracklist.get(position);
				if (holder.mItem != null) {
					holder.tv_title.setText(holder.mItem.getTitle());

					if (holder.mItem.getDuration() != null) {
						String duration = getDuraction(holder.mItem.getDuration());
						if (duration != null) {
							holder.tv_duration.setText(duration);
						}
					}
					ImageLoader mImageLoader;
					if (holder.mItem.getArtwork_url() != null) {
						mImageLoader = AppController.getInstance().getImageLoader();
						mImageLoader.get(holder.mItem.getArtwork_url(), ImageLoader.getImageListener(holder.imageView,
								R.drawable.ic_audiotrack_black, R.drawable.ic_audiotrack_black));
						holder.imageView.setImageUrl(holder.mItem.getArtwork_url(), mImageLoader);
					}

				}
			}
		}

		@Override
		public int getItemCount() {
			return arrTracklist.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public final View mView;
			public final NetworkImageView imageView;
			public final TextView tv_title;
			public final TextView tv_duration;
			public TrackDetails mItem;

			public ViewHolder(View view) {
				super(view);
				mView = view;
				imageView = (NetworkImageView) view.findViewById(R.id.iv_icon);
				tv_title = (TextView) view.findViewById(R.id.tv_title);
				application.setTypeface(tv_title, FONT_REVICONS, context);
				tv_duration = (TextView) view.findViewById(R.id.tv_duration);
				application.setTypeface(tv_duration, FONT_REVICONS, context);
				mView.setOnClickListener(this);
			}

			@Override
			public String toString() {
				return super.toString() + " '" + tv_title.getText() + "'";
			}

			@Override
			public void onClick(View v) {
				pos =getPosition();
				Log.d(TAG, "onClick " + getPosition());
				if (connDetector.checkInternetConnection()) {
					String trackId = null;
					String trackTitle = null;
					String trackImageUrl = null;
					if (mArrTrackList != null) {
						rl_musiccontorl.setVisibility(View.VISIBLE);
						card_view_musiccontorl.setVisibility(View.VISIBLE);
						trackId = mArrTrackList.get(pos).getId();
						trackTitle = mArrTrackList.get(pos).getTitle();
						trackImageUrl = mArrTrackList.get(pos).getArtwork_url();
						sharedPref.saveTrackId(trackId);
						sharedPref.saveTrackTitle(trackTitle);
						sharedPref.saveTrackImageUrl(trackImageUrl);
						HomeActivity.musicSrv.setSongDetails(mArrTrackList, pos);
						HomeActivity.musicSrv.playSong();
						/*PlayerConstants.SONG_PAUSED = false;
						PlayerConstants.SONG_NUMBER = pos;
						// PlayerConstants.SONG_ID = trackId;
						boolean isServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
						if (!isServiceRunning) {
							Intent i = new Intent(getApplicationContext(),SongService.class);
							startService(i);
						} else {
							PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
						}*/
					}
					resetplayer();
				} else {
					showCustomToast(getResources().getString(R.string.network_error));
					finish();
				}
			}
		}
	}

	private String getDuraction(String duration) {

		double totalTime = 0;

		String rduration;

		totalTime = Double.parseDouble(duration);

		long minutes = TimeUnit.MILLISECONDS.toMinutes((long) totalTime);

		long seconds = TimeUnit.MILLISECONDS.toSeconds((long) totalTime)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) totalTime));

		DecimalFormat precision = new DecimalFormat("00");

		rduration = precision.format(minutes) + ":" + precision.format(seconds);

		return rduration;
	}

}
