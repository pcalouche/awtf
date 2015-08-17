package com.pcalouche.awtf_reporting;

public class TagInstance {
	private int count;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public int incrementCount() {
		return (this.count++);
	}
}
