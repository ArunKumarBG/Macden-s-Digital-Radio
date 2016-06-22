package com.arunkumarbgcbe.macdensdigitalradio.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arun Kumar on 4/8/16.
 */
public class Track implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<TrackDetails> trackDetails;

	public ArrayList<TrackDetails> getTrackDetails() {
		return trackDetails;
	}

	public void setTrackDetails(ArrayList<TrackDetails> trackDetails) {
		this.trackDetails = trackDetails;
	}

	public static class TrackDetails implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String id;
		String user_id;
		String title;
		String label_name;
		String genre; // Category
		String description;
		Boolean downloadable;
		Boolean streamable;
		String tag_list;
		String duration;
		String created_at;
		String permalink_url;
		String artwork_url;
		String stream_url;
		String download_url;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPermalink_url() {
			return permalink_url;
		}

		public void setPermalink_url(String permalink_url) {
			this.permalink_url = permalink_url;
		}

		public String getArtwork_url() {
			return artwork_url;
		}

		public void setArtwork_url(String artwork_url) {
			this.artwork_url = artwork_url;
		}

		public String getStream_url() {
			return stream_url;
		}

		public void setStream_url(String stream_url) {
			this.stream_url = stream_url;
		}

		public String getDownload_url() {
			return download_url;
		}

		public void setDownload_url(String download_url) {
			this.download_url = download_url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getLabel_name() {
			return label_name;
		}

		public void setLabel_name(String label_name) {
			this.label_name = label_name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Boolean getDownloadable() {
			return downloadable;
		}

		public void setDownloadable(Boolean downloadable) {
			this.downloadable = downloadable;
		}

		public Boolean getStreamable() {
			return streamable;
		}

		public void setStreamable(Boolean streamable) {
			this.streamable = streamable;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		public String getTag_list() {
			return tag_list;
		}

		public void setTag_list(String tag_list) {
			this.tag_list = tag_list;
		}

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getCreated_at() {
			return created_at;
		}

		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}

		public int compareTo(TrackDetails arg0) {
			return this.title.compareTo(arg0.getTitle());
		}

	}

}
