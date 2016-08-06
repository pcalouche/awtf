package com.pcalouche.awtf_reporting;

import org.apache.commons.lang3.StringUtils;

public class ReporterStep {

	private String step;
	private String usage;
	private String example;
	private String javaClass;
	private int customOrder;
	private int count;

	public ReporterStep() {

	}

	public ReporterStep(String step, String usage, String example) {
		this(step, usage, example, "", 0);
	}

	public ReporterStep(String step, String usage, String example, String javaClass) {
		this(step, usage, example, javaClass, 0);
	}

	public ReporterStep(String step, String usage, String example, String javaClass, int customOrder) {
		this.step = StringUtils.isEmpty(step) ? "UNKNOWN STEP" : step;
		this.usage = StringUtils.isEmpty(usage) ? "UNKNOWN USAGE" : usage;
		this.example = StringUtils.isEmpty(example) ? "NO EXAMPLE PROVIDED" : example;
		this.javaClass = StringUtils.isEmpty(javaClass) ? "NO JAVA CLASS PROVIDED" : javaClass;
		this.customOrder = customOrder;
		this.count = 1;
	}

	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * @param usage
	 *            the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}

	/**
	 * @return the example
	 */
	public String getExample() {
		return example;
	}

	/**
	 * @param example
	 *            the example to set
	 */
	public void setExample(String example) {
		this.example = example;
	}

	/**
	 * @return the javaClass
	 */
	public String getJavaClass() {
		return javaClass;
	}

	/**
	 * @param javaClass
	 *            the javaClass to set
	 */
	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	/**
	 * @return the customOrder
	 */
	public int getCustomOrder() {
		return customOrder;
	}

	/**
	 * @param customOrder
	 *            the customOrder to set
	 */
	public void setCustomOrder(int customOrder) {
		this.customOrder = customOrder;
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
}