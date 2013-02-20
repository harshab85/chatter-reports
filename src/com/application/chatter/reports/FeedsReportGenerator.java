package com.application.chatter.reports;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.feeds.FeedException;
import com.application.chatter.feeds.FeedsFactory;
import com.application.chatter.feeds.IFeeds.IFeedData;
import com.application.chatter.profile.IProfile.IProfileData;
import com.application.chatter.profile.ProfileFactory;
import com.application.chatter.profile.ProfileReadException;

/**
 * Business Layer of Chatter Reports App
 * 
 * <br>
 * 
 * Used to retrieve the user profile information and feeds
 * 
 * @author hbalasubramanian
 *
 */
public class FeedsReportGenerator {

	/**
	 * Return the Chatter report for the logged in user 
	 * 
	 * @throws ProfileReadException
	 * @throws FeedException
	 */
	public static ChatterFeedsReport getReport(IApplicationInfo applicationInfo, String profileType, String feedType) throws ProfileReadException, FeedException{
		
		IProfileData profileData = ProfileFactory.getFactory().getProfile(applicationInfo, profileType);		
		IFeedData feedData =  FeedsFactory.getFactory().getFeeds(applicationInfo, feedType);
		
		ChatterFeedsReport feedsReport = new ChatterFeedsReport();
		feedsReport.setProfileData(profileData);
		feedsReport.setFeedsData(feedData);
		
		return feedsReport;
	}
}
