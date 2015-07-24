package com.pcalouche.awtf_core.util.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Enum for Row Actions
 *
 * @author Philip Calouche
 *
 */
public enum RowAction {
	SELECT("select"),
	DESELECT("deselect"),
	CLICK("click"),
	EXPAND("expand"),
	COLLAPSE("collapse"),
	CAN_SELECT("can select"),
	CANNOT_SELECT("cannot select"),
	SEE("see"),
	DO_NOT_SEE("do not see"),
	SEE_SELECTED("see selected"),
	SEE_DESELECTED("see deselected");

	private String description;

	RowAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static RowAction getByDescription(String description) {
		for (RowAction rowAction : RowAction.values()) {
			if (rowAction.getDescription().equals(description)) {
				return rowAction;
			}
		}
		// Return null if we get here
		return null;
	}

	public static String getValidDescriptions() {
		List<String> validDescriptions = new ArrayList<String>();
		for (RowAction rowAction : RowAction.values()) {
			validDescriptions.add(rowAction.getDescription());
		}
		return StringUtils.join(validDescriptions, ", ");
	}
}
