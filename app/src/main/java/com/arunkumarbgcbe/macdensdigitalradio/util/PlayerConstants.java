package com.arunkumarbgcbe.macdensdigitalradio.util;

import java.util.ArrayList;
import android.os.Handler;

import com.arunkumarbgcbe.macdensdigitalradio.data.Track;

public class PlayerConstants {
	//List of Songs
	public static ArrayList<Track.TrackDetails> SONGS_LIST = new ArrayList<Track.TrackDetails>();
	//Object of Songs
	public static Track OBJ_TRACK = new Track();
	//song number which is playing right now from SONGS_LIST
	public static String CATEGORY = null;
	//song number which is playing right now from SONGS_LIST
	public static int SONG_NUMBER = 0;
	//song id which is playing right now from SONGS_LIST
	public static String SONG_ID = null;
	//song is playing or paused
	public static boolean SONG_PAUSED = true;
	//song changed (next, previous)
	public static boolean SONG_CHANGED = false;
	//handler for song changed(next, previous) defined in service(SongService)
	public static Handler SONG_CHANGE_HANDLER;
	//handler for song play/pause defined in service(SongService)
	public static Handler PLAY_PAUSE_HANDLER;
	//handler for showing song progress defined in Activities(MainActivity, AudioPlayerActivity)
	public static Handler PROGRESSBAR_HANDLER;
}
