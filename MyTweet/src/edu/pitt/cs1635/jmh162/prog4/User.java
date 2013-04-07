package edu.pitt.cs1635.jmh162.prog4;

public class User {
	private String username;
	private String name;
	private int numTweets;
	private int numFollowers;
	private int numFollowing;

	public void setUsername(String newUsername) {
		username = newUsername;
	}
	public String getUsername() {
		return username;
	}

	public void setName(String newName) {
		name = newName;
	}
	public String getName() {
		return name;
	}

	public void setNumtweets(int newNumTweets) {
		numTweets = newNumTweets;
	}
	public int getNumtweets() {
		return numTweets;
	}

	public void setNumFollowers(int newNumFollowers) {
		numFollowers = newNumFollowers;
	}
	public int getNumFollowers() {
		return numFollowers;
	}
	
	public void setNumFollowing(int newNumFollowing) {
		numFollowing = newNumFollowing;
	}
	public int getNumFollowing() {
		return numFollowing;
	}

}
