package com.pcalouche.awtf_reporting;

public class TagInstance {
	private String tagName;
	private int count;

	public TagInstance(String tagName) {
		this.tagName = tagName;
		this.count = 1;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TagInstance other = (TagInstance) obj;
		if (tagName == null) {
			if (other.tagName != null) {
				return false;
			}
		} else if (!tagName.equals(other.tagName)) {
			return false;
		}
		return true;
	}
}
