package com.arunkumarbgcbe.macdensdigitalradio.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.arunkumarbgcbe.macdensdigitalradio.activity.HomeActivity;
import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.data.Track.TrackDetails;
import com.arunkumarbgcbe.macdensdigitalradio.receiver.NotificationBroadcast;
import com.arunkumarbgcbe.macdensdigitalradio.util.Config;
import com.arunkumarbgcbe.macdensdigitalradio.util.PlayerConstants;
import com.arunkumarbgcbe.macdensdigitalradio.util.UtilFunctions;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;


public class MusicService extends Service
		implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, OnCompletionListener {

	public static final String TAG = MusicService.class.getName();

	private static final int NOTIFY_ID = 1;
	public static final String NOTIFY_PREVIOUS = "com.arunkumarbgcbe.macdensdigitalradio.previous";
	public static final String NOTIFY_DELETE = "com.arunkumarbgcbe.macdensdigitalradio.delete";
	public static final String NOTIFY_PAUSE = "com.arunkumarbgcbe.macdensdigitalradio.pause";
	public static final String NOTIFY_PLAY = "com.arunkumarbgcbe.macdensdigitalradio.play";
	public static final String NOTIFY_NEXT = "com.arunkumarbgcbe.macdensdigitalradio.next";

	private ComponentName remoteComponentName;
	private RemoteControlClient remoteControlClient;

	AudioManager audioManager;

	// media mMediaPlayer
	private MediaPlayer mMediaPlayer;
	// mTrack list
	private ArrayList<TrackDetails> mTrackList;
	// current position
	private int mTrackIndex;

	private String mTrackTitle = "";

	private double startTime = 0;
	private double finalTime = 0;

	private final IBinder musicBind = new MusicBinder();

	private boolean shuffle = false;
	private Random rand;
	
	private static boolean currentVersionSupportBigNotification = false;
	private static boolean currentVersionSupportLockScreenControls = false;
	
	int NOTIFICATION_ID = 1111;

	public void onCreate() {

		// create the service
		super.onCreate();
		
		currentVersionSupportBigNotification = UtilFunctions.currentVersionSupportBigNotification();
        currentVersionSupportLockScreenControls = UtilFunctions.currentVersionSupportLockScreenControls();
		rand = new Random();

		// initialize position
		mTrackIndex = 0;
		// create mMediaPlayer
		mMediaPlayer = new MediaPlayer();

		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		initMusicPlayer();
		
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Log.d(TAG, "setOnCompletionListener");
				playNext();
			}
		});
	}

	public void initMusicPlayer() {
		// set mMediaPlayer properties
		mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);

	}

	public class MusicBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	public void setList(ArrayList<TrackDetails> theSongs) {
		mTrackList = theSongs;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return musicBind;
	}

	@SuppressLint("NewApi")
	public void playSong() {
		// play a mTrack
		mMediaPlayer.reset();
		// get mTrack
		if (mTrackList != null) {
			TrackDetails playSong = mTrackList.get(mTrackIndex);
			// get id
			// long currSong = Double.parseDouble(playSong.getId());
			// set uri
			Uri trackUri = Uri.parse(playSong.getStream_url());

			try {
				mMediaPlayer = new MediaPlayer();
				// Log.d(TAG, "playSong.getStream_url() : " +
				// playSong.getStream_url());
				mMediaPlayer.setDataSource(playSong.getStream_url() + "?client_id=" + Config.CLIENT_ID);
				mMediaPlayer.setOnPreparedListener(this);
				mMediaPlayer.prepareAsync();

				finalTime = mMediaPlayer.getDuration();
				startTime = mMediaPlayer.getCurrentPosition();

				// Log.e(TAG, "Track Info : " + mMediaPlayer.getTrackInfo());

				/*
				 * if (oneTimeOnly == 0) { seekbar.setMax((int) finalTime);
				 * oneTimeOnly = 1; }
				 */
				Log.d(TAG,
						"finalTime : " + String.format("%d : %d",
								TimeUnit.MILLISECONDS.toMinutes(
										(long) finalTime),
						TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
								- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));

				Log.d(TAG,
						"startTime : " + String.format("%d : %d",
								TimeUnit.MILLISECONDS.toMinutes(
										(long) startTime),
						TimeUnit.MILLISECONDS.toSeconds((long) startTime)
								- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
				if(currentVersionSupportLockScreenControls){
					RegisterRemoteClient();
				}
				if(currentVersionSupportLockScreenControls){
					remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
				}

			newNotification();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("MUSIC SERVICE", "Error setting data source", e);
			}
		}

	}

	@Override
	public boolean onUnbind(Intent intent) {
		mMediaPlayer.stop();
		mMediaPlayer.release();
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp){
		// TODO Auto-generated method stub
		Log.d(TAG, "onCompletion");
		playNext();
		/*if (mp.getCurrentPosition() < 0) {
			Log.d(TAG, "mp.getCurrentPosition()");
			mTrackIndex++;
			Log.d(TAG, "mTrackIndex : " +mTrackIndex);
			Log.d(TAG, "mTrackList.size() : " +mTrackList.size());
			if(mTrackIndex<=mTrackList.size())
			{
				mp.reset();
				//playNext();
				playSong();
			}
		}*/
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		mp.reset();
		return false;

	}

	@SuppressLint("NewApi")
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// start playback
		mp.start();
	}

	public void setSongDetails(ArrayList<TrackDetails> trackDetails, int mTrack) {
		initMusicPlayer();
		onPrepared(mMediaPlayer);
		mTrackList = trackDetails;
		mTrackIndex = mTrack;

		mTrackTitle = mTrackList.get(mTrackIndex).getTitle();
		playSong();
	}

	public int getPosn() {
		return mMediaPlayer.getCurrentPosition();
	}

	public int getDur() {
		return mMediaPlayer.getDuration();
	}

	public boolean isPng() {
		return mMediaPlayer.isPlaying();
	}

	public void pausePlayer() {
		mMediaPlayer.pause();
	}

	@SuppressLint("NewApi")
	private void showNotification() {
		// TODO Auto-generated method stub
		Intent notIntent = new Intent(this, HomeActivity.class);
		notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(this);

		builder.setContentIntent(pendInt).setSmallIcon(R.drawable.ic_music_video_black).setTicker(mTrackTitle)
				.setOngoing(true).setContentTitle("Playing").setContentText(mTrackTitle);
		Notification not = builder.build();

		startForeground(NOTIFY_ID, not);
	}


	@SuppressLint("NewApi")
	private void RegisterRemoteClient(){
		remoteComponentName = new ComponentName(getApplicationContext(), new NotificationBroadcast().ComponentName());
		try {
			if(remoteControlClient == null) {
				audioManager.registerMediaButtonEventReceiver(remoteComponentName);
				Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
				mediaButtonIntent.setComponent(remoteComponentName);
				PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
				remoteControlClient = new RemoteControlClient(mediaPendingIntent);
				audioManager.registerRemoteControlClient(remoteControlClient);
			}
			remoteControlClient.setTransportControlFlags(
					RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
							RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
							RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE |
							RemoteControlClient.FLAG_KEY_MEDIA_STOP |
							RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS |
							RemoteControlClient.FLAG_KEY_MEDIA_NEXT);
		}catch(Exception ex) {
		}
	}
	
	/**
	 * Notification
	 * Custom Bignotification is available from API 16
	 */
	@SuppressLint("NewApi")
	private void newNotification() {
		String songName = mTrackList.get(mTrackIndex).getTitle();
		String albumName = mTrackList.get(mTrackIndex).getGenre();
		RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(),R.layout.custom_notification);
		RemoteViews expandedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.big_notification);
		 
		Notification notification = new NotificationCompat.Builder(getApplicationContext())
        .setSmallIcon(R.drawable.ic_audiotrack_gray)
        .setContentTitle(songName).build();

		setListeners(simpleContentView);
		setListeners(expandedView);
		
		notification.contentView = simpleContentView;
		if(currentVersionSupportBigNotification){
			notification.bigContentView = expandedView;
		}
		
		try{
			/*long albumId = Integer.parseInt(PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getId());
			Bitmap albumArt = UtilFunctions.getAlbumart(getApplicationContext(), albumId);*/
			String albumUrl = mTrackList.get(mTrackIndex).getGenre();
			URL url = new URL(albumUrl);
			Bitmap albumArt = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			if(albumArt != null){
				notification.contentView.setImageViewBitmap(R.id.imageViewAlbumArt, albumArt);
				if(currentVersionSupportBigNotification){
					notification.bigContentView.setImageViewBitmap(R.id.imageViewAlbumArt, albumArt);
				}
			}else{
				notification.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_audiotrack_gray);
				if(currentVersionSupportBigNotification){
					notification.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_audiotrack_gray);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(PlayerConstants.SONG_PAUSED){
			notification.contentView.setViewVisibility(R.id.btnPause, View.GONE);
			notification.contentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);

			if(currentVersionSupportBigNotification){
				notification.bigContentView.setViewVisibility(R.id.btnPause, View.GONE);
				notification.bigContentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);
			}
		}else{
			notification.contentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
			notification.contentView.setViewVisibility(R.id.btnPlay, View.GONE);

			if(currentVersionSupportBigNotification){
				notification.bigContentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
				notification.bigContentView.setViewVisibility(R.id.btnPlay, View.GONE);
			}
		}

		notification.contentView.setTextViewText(R.id.textSongName, songName);
		notification.contentView.setTextViewText(R.id.textAlbumName, albumName);
		if(currentVersionSupportBigNotification){
			notification.bigContentView.setTextViewText(R.id.textSongName, songName);
			notification.bigContentView.setTextViewText(R.id.textAlbumName, albumName);
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		startForeground(NOTIFICATION_ID, notification);
	}

	/**
	 * Notification click listeners
	 * @param view
	 */
	public void setListeners(RemoteViews view) {
		Intent previous = new Intent(NOTIFY_PREVIOUS);
		Intent delete = new Intent(NOTIFY_DELETE);
		Intent pause = new Intent(NOTIFY_PAUSE);
		Intent next = new Intent(NOTIFY_NEXT);
		Intent play = new Intent(NOTIFY_PLAY);

		PendingIntent pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnPrevious, pPrevious);

		PendingIntent pDelete = PendingIntent.getBroadcast(getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnDelete, pDelete);

		PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnPause, pPause);

		PendingIntent pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnNext, pNext);

		PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnPlay, pPlay);

	}

	public void seek(int posn) {
		mMediaPlayer.seekTo(posn);
	}

	public void go() {
		mMediaPlayer.start();
	}

	public void playPrev() {
		mTrackIndex--;
		if (mTrackIndex != 0)
			mTrackIndex = mTrackList.size() - 1;
		playSong();
	}

	@Override
	public void onDestroy() {
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
			mMediaPlayer = null;
		}
		stopForeground(true);
	}

	public void setShuffle() {
		if (shuffle)
			shuffle = false;
		else
			shuffle = true;
	}

	public void playNext() {
		if (mTrackList!=null) {
			if (shuffle) {
				int newSong = mTrackIndex;
				while (newSong == mTrackIndex) {
					newSong = rand.nextInt(mTrackList.size());
				}
				mTrackIndex = newSong;
			} else {
				mTrackIndex++;
				if (mTrackIndex <= mTrackList.size())
					mTrackIndex = 0;
			}
			playSong();
		}

	}

}
