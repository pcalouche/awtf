package com.pcalouche.awtf_app_example;

import java.net.URL;
import java.net.URLClassLoader;

public class TestDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url.getFile());
		}
	}

}
