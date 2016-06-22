package com.arunkumarbgcbe.macdensdigitalradio.util;

import java.io.FileDescriptor;
import java.util.ArrayList;

import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.data.Track;



import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilFunctions {
	static String LOG_CLASS = "UtilFunctions";

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

	public static ArrayList<Track.TrackDetails> getSongsList(){

		ArrayList<Track.TrackDetails> mArrTrackList = new ArrayList<Track.TrackDetails>();
		if(PlayerConstants.SONGS_LIST!=null)
		{
			for (int i = 0; i < PlayerConstants.SONGS_LIST.size(); i++) {
				if (PlayerConstants.SONGS_LIST.get(i).getGenre().equalsIgnoreCase(PlayerConstants.CATEGORY)) {
					mArrTrackList.add(PlayerConstants.SONGS_LIST.get(i));
				}
			}
		}
		return mArrTrackList;
	}


	/**
	 * Read the songs present in external storage
	 * @param context
	 * @return
	 */
	/*public static ArrayList<Track.TrackDetails> listOfSongs(Context context){
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor c = context.getContentResolver().query(uri, null, MediaStore.Audio.Media.IS_MUSIC + " != 0", null, null);
		ArrayList<Track.TrackDetails> listOfSongs = new ArrayList<Track.TrackDetails>();
		c.moveToFirst();
		while(c.moveToNext()){
			Track.TrackDetails songData = new Track.TrackDetails();
			
			String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
			String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
			long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
			String data = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
			long albumId = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
			String composer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
			
			songData.setTitle(title);
			songData.setAlbum(album);
			songData.setArtist(artist);
			songData.setDuration(duration);
			songData.setPath(data);
			songData.setAlbumId(albumId);
			songData.setComposer(composer);
			listOfSongs.add(songData);
		}
		c.close();
		Log.d("SIZE", "SIZE: " + listOfSongs.size());
		return listOfSongs;
	}*/


	public static Track jsonParesing(Context context,String response) {
		// TODO Auto-generated method stub
		Track mObjTrack = new Track();
		ArrayList<Track.TrackDetails> mArrAllTrackDetails = new ArrayList<Track.TrackDetails>();
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				Track.TrackDetails trackDetails = new Track.TrackDetails();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				trackDetails.setId(jsonObject.getString(context.getString(R.string.key_id)));
				trackDetails.setTitle(jsonObject.getString(context.getString(R.string.key_title)));
				trackDetails.setUser_id(jsonObject.getString(context.getString(R.string.key_user_id)));
				trackDetails.setLabel_name(jsonObject.getString(context.getString(R.string.key_label_name)));
				trackDetails.setGenre(jsonObject.getString(context.getString(R.string.key_genre)));
				trackDetails.setDescription(jsonObject.getString(context.getString(R.string.key_description)));
				trackDetails.setDownloadable(jsonObject.getBoolean(context.getString(R.string.key_downloadable)));
				trackDetails.setStreamable(jsonObject.getBoolean(context.getString(R.string.key_streamable)));
				if (jsonObject.getString(context.getString(R.string.key_tag_list)) != null) {
					trackDetails.setTag_list(jsonObject.getString(context.getString(R.string.key_tag_list)));
				}
				if (jsonObject.getString(context.getString(R.string.key_duration)) != null) {
					trackDetails.setDuration(jsonObject.getString(context.getString(R.string.key_duration)));
				}
				if (jsonObject.getString(context.getString(R.string.key_created_at)) != null) {
					trackDetails.setCreated_at(jsonObject.getString(context.getString(R.string.key_created_at)));
				}
				if (jsonObject.getString(context.getString(R.string.key_permalink_url)) != null) {
					trackDetails.setPermalink_url(jsonObject.getString(context.getString(R.string.key_permalink_url)));
				}
				if (jsonObject.getString(context.getString(R.string.key_artwork_url)) != null) {
					trackDetails.setArtwork_url(jsonObject.getString(context.getString(R.string.key_artwork_url)));
				}
				if (jsonObject.getString(context.getString(R.string.key_stream_url)) != null) {
					trackDetails.setStream_url(jsonObject.getString(context.getString(R.string.key_stream_url)));
				}
				if (!jsonObject.isNull(context.getString(R.string.key_download_url))) {
					if (jsonObject.getString(context.getString(R.string.key_download_url)) != null) {
						trackDetails.setDownload_url(jsonObject.getString(context.getString(R.string.key_download_url)));
					}
				}
				mArrAllTrackDetails.add(trackDetails);
			}
			mObjTrack.setTrackDetails(mArrAllTrackDetails);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PlayerConstants.OBJ_TRACK = mObjTrack;
		return mObjTrack;
	}

	/**
	 * Get the album image from albumId
	 * @param context
	 * @param album_id
	 * @return
	 */
	public static Bitmap getAlbumart(Context context,Long album_id){
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    try{
	        final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
	        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
	        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
	        if (pfd != null){
	            FileDescriptor fd = pfd.getFileDescriptor();
	            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
	            pfd = null;
	            fd = null;
	        }
	    } catch(Error ee){}
	    catch (Exception e) {}
	    return bm;
	}

	/**
	 * @param context
	 * @return
	 */
	/*public static Bitmap getDefaultAlbumArt(Context context){
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    try{
	    	bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_album_art, options);
	    } catch(Error ee){}
	    catch (Exception e) {}
	    return bm;
	}*/
	/**
	 * Convert milliseconds into time hh:mm:ss
	 * @param milliseconds
	 * @return time in String
	 */
	public static String getDuration(long milliseconds) {
		long sec = (milliseconds / 1000) % 60;
		long min = (milliseconds / (60 * 1000))%60;
		long hour = milliseconds / (60 * 60 * 1000);

		String s = (sec < 10) ? "0" + sec : "" + sec;
		String m = (min < 10) ? "0" + min : "" + min;
		String h = "" + hour;
		
		String time = "";
		if(hour > 0) {
			time = h + ":" + m + ":" + s;
		} else {
			time = m + ":" + s;
		}
		return time;
	}
	
	public static boolean currentVersionSupportBigNotification() {
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if(sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
			return true;
		}
		return false;
	}
	
	public static boolean currentVersionSupportLockScreenControls() {
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if(sdkVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			return true;
		}
		return false;
	}
}
