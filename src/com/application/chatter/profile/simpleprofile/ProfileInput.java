package com.application.chatter.profile.simpleprofile;

import java.net.URL;
import java.util.List;

public class ProfileInput {
	
	private String lastName = null;

	private String firstName = null;

	private URL photoURL = null;

	private URL thumbnail = null;

	private List<String> groups = null;

	private List<String> users = null;

	private List<String> feeds = null;

	private List<String> feedItems = null;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public URL getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(URL photoURL) {
		this.photoURL = photoURL;
	}

	public URL getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(URL thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}

	public List<String> getFeedItems() {
		return feedItems;
	}

	public void setFeedItems(List<String> feedItems) {
		this.feedItems = feedItems;
	}

}