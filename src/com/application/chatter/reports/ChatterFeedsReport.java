package com.application.chatter.reports;

import com.application.chatter.feeds.IFeeds.IFeedData;
import com.application.chatter.profile.IProfile.IProfileData;

/**
 * Bean to store the user's profile data and feeds
 * 
 * @author hbalasubramanian
 *
 */
public class ChatterFeedsReport {

	IProfileData profileData = null;
	
	IFeedData feedsData = null;

	public IProfileData getProfileData() {
		return profileData;
	}

	public void setProfileData(IProfileData profileData) {
		this.profileData = profileData;
	}

	public IFeedData getFeedsData() {
		return feedsData;
	}

	public void setFeedsData(IFeedData feeds) {
		this.feedsData = feeds;
	}
	
	
}
