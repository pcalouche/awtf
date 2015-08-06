package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.TestEnvironmentConfig;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 *
 */
public class MyAppTestEnvironmentConfig extends TestEnvironmentConfig {
	private String loginID;
	private String password;

	/**
	 * @return the loginID
	 */
	public String getLoginID() {
		return loginID;
	}

	/**
	 * @param loginID
	 *            the loginID to set
	 */
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
