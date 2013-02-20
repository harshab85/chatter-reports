package com.application.chatter.feeds.simplefeed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bean to store the feed data
 * 
 * @author hbalasubramanian
 *
 */
public class Feed {

	private Date creationDate = null;
	
	private String post = null;
	
	private String type = null;
	
	private Long numLikes = null;
	
	private Long numComments = null;

	private List<Feed> comments = new ArrayList<Feed>();
	
	private String fullPost = null;
	
	public List<Feed> getComments() {
		return comments;
	}

	public void setComments(List<Feed> comments) {
		this.comments = comments;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getFullPost() {
		return fullPost;
	}

	public void setFullPost(String fullPost) {
		this.fullPost = fullPost;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getLikes() {
		return numLikes;
	}

	public void setLikes(Long likes) {
		this.numLikes = likes;
	}

	public Long getNumComments() {
		return numComments;
	}

	public void setNumComments(Long numComments) {
		this.numComments = numComments;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("POST: ");
		sb.append(this.post);
		
		sb.append(" TYPE: ");
		sb.append(this.type);
		
		sb.append(" CREATIONDATE: ");
		sb.append(this.creationDate);
		
		sb.append(" LIKES: ");
		sb.append(this.numLikes);
		
		sb.append(" NUM COMMENTS: ");
		sb.append(this.numComments);
		
		sb.append(" COMMENTS: ");
		sb.append(this.comments.toString());
		
		return sb.toString();
	}
}
