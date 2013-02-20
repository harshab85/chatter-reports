package com.application.chatter.profile;

import java.net.URL;
import java.util.List;

/**
 * 
 * An interface to store the user's profile information
 * 
 * @author hbalasubramanian
 *
 */
public interface IProfile {

	interface IProfileData{
		
		public String getLastName();
		
		public String getFirstName();
		
		public URL getPhotoURL();
		
		public URL getThumbnailURL();
		
		public List<String> getGroups();
		
		public List<String> getUsers();
		
		public List<String> getFeeds();
		
		public List<String> getFeedItems();
	}
	
	/**
	 * Returns the current user's profile data
	 * 
	 * @return
	 */
	public IProfileData getProfile() throws ProfileReadException;
}
