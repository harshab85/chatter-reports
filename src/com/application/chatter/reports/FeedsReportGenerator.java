package com.application.chatter.reports;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.feeds.FeedException;
import com.application.chatter.feeds.FeedsFactory;
import com.application.chatter.feeds.IFeeds.IFeedData;
import com.application.chatter.profile.IProfile.IProfileData;
import com.application.chatter.profile.ProfileFactory;
import com.application.chatter.profile.ProfileReadException;

public class FeedsReportGenerator {

	public static ChatterFeedsReport getReport(IApplicationInfo applicationInfo, String profileType, String feedType) throws ProfileReadException, FeedException{
		
		IProfileData profileData = ProfileFactory.getFactory().getProfile(applicationInfo, profileType);		
		IFeedData feedData =  FeedsFactory.getFactory().getFeeds(applicationInfo, feedType);
		
		ChatterFeedsReport feedsReport = new ChatterFeedsReport();
		feedsReport.setProfileData(profileData);
		feedsReport.setFeedsData(feedData);
		
		return feedsReport;
	}
}
