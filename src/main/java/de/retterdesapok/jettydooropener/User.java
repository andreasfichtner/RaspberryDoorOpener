package de.retterdesapok.jettydooropener;

public class User {
	
	private int ID = 0;
	private String username = null;
	private String passwordhash = null;
	private int failedLoginCount = Utilities.MAX_LOGIN_ATTEMPTS;
	private int remainingLogins = 0;

	public User(int ID, String username, String passwordhash, int failedLoginCount, int remainingLogins) {
		this.ID = ID;
		this.username = username;
		this.passwordhash = passwordhash;
		this.failedLoginCount = failedLoginCount;
		this.remainingLogins = remainingLogins;
	}
	
	public int getID() {
		return ID;
	}
	public String getUsername() {
		return username;
	}
	public String getPasswordhash() {
		return passwordhash;
	}
	public int getFailedLoginCount() {
		return failedLoginCount;
	}
	public int getRemainingLogins() {
		return remainingLogins;
	}
}
