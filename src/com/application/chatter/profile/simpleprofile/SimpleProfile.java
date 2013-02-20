package com.application.chatter.profile.simpleprofile;

import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.profile.IProfile;
import com.application.chatter.profile.ProfileReadException;
import com.application.chatter.reports.MissingInputException;
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
			profileData = createProfileData(json);			
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
	protected IProfileData createProfileData(JSONObject json) throws IOException, ParseException, MissingInputException{
		
		if(json == null){
			throw new MissingInputException("Unable to read the profile information");
		}
		
		ProfileInput profileInput = new ProfileInput();
		
		Object fName = json.get(UserProfileKeys.FIRST_NAME.getKey());
		if(fName instanceof String){
			profileInput.setFirstName((String)fName);
		}
		
		Object lName = json.get(UserProfileKeys.LAST_NAME.getKey());
		if(lName instanceof String){
			profileInput.setLastName((String)lName);
		}
	
		Object jPhotos = json.get(UserProfileKeys.PHOTOS.getKey());
		JSONObject photos = null;
		if(jPhotos instanceof JSONObject){
			photos = (JSONObject)jPhotos;
		}
		
		if(photos != null){			
			Object picture = photos.get(UserProfileKeys.PICTURE.getKey());
			if(picture instanceof String){
				String pictureURLString = (String)picture;
				URL pictureURL = new URL(pictureURLString);
				profileInput.setPhotoURL(pictureURL);
			}
		}
		
		if(photos != null){
			Object thumbnail = photos.get(UserProfileKeys.THUMBNAIL.getKey());
			if(thumbnail instanceof String){
				String thumbnailURLString = (String)thumbnail;
				URL thumbnailURL = new URL(thumbnailURLString);
				profileInput.setThumbnail(thumbnailURL);
			}
		}
		
		IProfileData profileData = new SimpleProfileBean(profileInput);
		return profileData;
	}
}
