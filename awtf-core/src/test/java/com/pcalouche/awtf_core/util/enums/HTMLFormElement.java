package com.pcalouche.awtf_core.util.enums;

/**
 * Enum for HTML form elements
 *
 * @author Philip Calouche
 *
 */
public enum HTMLFormElement {
	input,
	textarea,
	select;

	public static boolean isFormElement(String tag) {
		return true;
	}
}
