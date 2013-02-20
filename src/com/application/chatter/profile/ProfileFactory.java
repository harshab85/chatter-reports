package com.application.chatter.profile;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.profile.IProfile.IProfileData;
import com.application.chatter.profile.simpleprofile.SimpleProfile;
import com.application.chatter.util.UserInfoUtil.ProfileType;

public class ProfileFactory {

	private static final ProfileFactory profileFactory = new ProfileFactory();
	
	private ProfileFactory(){
		
	}
	
	public static ProfileFactory getFactory(){
		return profileFactory;
	}
	
	public IProfileData getProfile(IApplicationInfo applicationInfo, String profileType) throws ProfileReadException{
		
		if(profileType == null || profileType.isEmpty() || profileType.equalsIgnoreCase(ProfileType.SIMPLE.getProfileType())){
			IProfile profile = new SimpleProfile(applicationInfo);
			return profile.getProfile();
		}
		else{
			throw new UnsupportedOperationException("The prfile type is not supported : " + profileType);
		}
	}
}
