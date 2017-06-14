package com.zyc.processor;

import java.io.IOException;

public class Rukou {
	public static void main(String[] args) {
		LoginProcessor loginProcessor = new LoginProcessor();
		try {
			loginProcessor.login();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
