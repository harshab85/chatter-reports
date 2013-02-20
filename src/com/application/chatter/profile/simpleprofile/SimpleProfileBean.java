package com.application.chatter.profile.simpleprofile;

import java.net.URL;
import java.util.List;

import com.application.chatter.profile.IProfile.IProfileData;


public class SimpleProfileBean implements IProfileData{

	private ProfileInput profileInput = null;
	
	public SimpleProfileBean(ProfileInput profileInput){
		this.profileInput = profileInput;
	}
	
	public String getLastName() {
		return profileInput.getLastName();
	}

	public String getFirstName() {
		return profileInput.getFirstName();
	}

	public URL getPhotoURL() {
		return profileInput.getPhotoURL();
	}

	@Override
	public URL getThumbnailURL() {
		return profileInput.getThumbnail();
	}

	@Override
	public List<String> getGroups() {
		return profileInput.getGroups();
	}

	@Override
	public List<String> getUsers() {
		return profileInput.getUsers();
	}

	@Override
	public List<String> getFeeds() {
		return profileInput.getFeeds();
	}

	@Override
	public List<String> getFeedItems() {
		return profileInput.getFeedItems();
	}	
}
