package com.application.chatter.profile.simpleprofile;

import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.profile.IProfile;
import com.application.chatter.profile.ProfileReadException;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.UserInfoUtil;
import com.application.chatter.util.UserInfoUtil.UserProfileKeys;

/**
 * A basic implemetation of {@link IProfile}
 * 
 * <br>
 * 
 * Return a logged-in user's first name, last name and profile photo
 * 
 * @author hbalasubramanian
 *
 */
public class SimpleProfile implements IProfile {

	IApplicationInfo applicationInfo = null;
	
	public SimpleProfile(IApplicationInfo applicationInfo){
		this.applicationInfo = applicationInfo;
	}
	
	@Override
	public IProfileData getProfile() throws ProfileReadException {
		
		IProfileData profileData = null;
		
		try{
			String identityService = applicationInfo.getIdentityServiceURL();
							
			JSONObject json = UserInfoUtil.getJSONResponse(identityService, applicationInfo.getToken().getValue());
			if(json != null){
				profileData = createProfileData(json);
			}
			else{
				throw new Exception("Unable to read the profile information");
			}
		}
		catch (Exception e) {
			throw new ProfileReadException(ApplicationUtil.getErrorMessage(e));
		}
		
		return profileData;
	}

	/**
	 * Creates a simple profile for the user
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	protected IProfileData createProfileData(JSONObject json) throws IOException, ParseException{
		
		ProfileInput profileInput = new ProfileInput();
		profileInput.setFirstName((String)json.get(UserProfileKeys.FIRST_NAME.getKey()));
		profileInput.setLastName((String)json.get(UserProfileKeys.LAST_NAME.getKey()));
	
		JSONObject photos = (JSONObject)json.get(UserProfileKeys.PHOTOS.getKey());
		
		String pictureURLString = (String)photos.get(UserProfileKeys.PICTURE.getKey());
		URL pictureURL = new URL(pictureURLString);
		profileInput.setPhotoURL(pictureURL);
		
		String thumbnailURLString = (String)photos.get(UserProfileKeys.THUMBNAIL.getKey());
		URL thumbnailURL = new URL(thumbnailURLString);
		profileInput.setThumbnail(thumbnailURL);
		
		IProfileData profileData = new SimpleProfileBean(profileInput);
		return profileData;
	}
}
