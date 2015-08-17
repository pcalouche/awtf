package com.pcalouche.awtf_reporting;

import org.apache.commons.lang3.StringEscapeUtils;

public class ReporterStep implements Comparable<Object> {

	private String step;
	private String usage;
	private String example;
	private int manualOrder;
	private int count;

	public ReporterStep(String step, String usage, String example) {
		this.step = step;
		this.usage = usage;
		this.example = example;
		if ("".equals(this.usage)) {
			this.usage = "UNKNOWN STEP";
		}
		this.count = this.incrementCount();
	}

	public ReporterStep() {

	}

	public String getStep() {
		return StringEscapeUtils.escapeJson(this.step);
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getUsage() {
		return StringEscapeUtils.escapeJson(this.usage);
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getExample() {
		return StringEscapeUtils.escapeJson(example);
	}

	public void setExample(String example) {
		this.example = example;
	}

	public int getManualOrder() {
		return manualOrder;
	}

	public void setManualOrder(int order) {
		this.manualOrder = order;
	}

	public int getCount() {
		return count;
	}

	public int incrementCount() {
		return (this.count++);
	}

	@Override
	public int compareTo(Object arg0) {
		ReporterStep step = (ReporterStep) arg0;
		return this.count - step.count;
		// For Manual Order
		// return this.manualOrder - step.manualOrder;
	}
}