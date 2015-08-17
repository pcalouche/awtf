package com.pcalouche.awtf_app_example;

import org.apache.logging.log4j.LogManager;

import com.pcalouche.awtf_core.CoreStepHandler;

public class MyAppStepHandler extends CoreStepHandler {

	public MyAppStepHandler() {
		logger = LogManager.getLogger();
	}
}
