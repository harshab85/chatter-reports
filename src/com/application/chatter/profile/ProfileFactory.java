package com.application.chatter.profile;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.profile.IProfile.IProfileData;
import com.application.chatter.profile.simpleprofile.SimpleProfile;
import com.application.chatter.profile.simpleprofile.SimpleProfileBean;
import com.application.chatter.util.UserInfoUtil.ProfileType;

/**
 * A factory class to create profile instances using the request's 'profile type' parameter
 * 
 * @author hbalasubramanian
 *
 */
public class ProfileFactory {

	private static final ProfileFactory profileFactory = new ProfileFactory();
	
	private ProfileFactory(){
	
	}
	
	/**
	 * Eagerly initialized singleton
	 * 
	 */
	public static ProfileFactory getFactory(){
		return profileFactory;
	}
	
	/**
	 * Create a new profile bean using the profile type.
	 * 
	 *  <br>
	 *  
	 *  By default the factory creates {@link SimpleProfileBean}
	 * 
	 * @throws ProfileReadException
	 * @throws {@link UnsupportedOperationException} when the profile type in the request is not supported
	 */
	public IProfileData getProfile(IApplicationInfo applicationInfo, String profileType) throws ProfileReadException{
		
		if(profileType == null || profileType.isEmpty() || profileType.equalsIgnoreCase(ProfileType.SIMPLE.getProfileType())){
			IProfile profile = new SimpleProfile(applicationInfo);
			return profile.getProfile();
		}
		else{
			throw new UnsupportedOperationException("The profile type is not supported : " + profileType);
		}
	}
}
