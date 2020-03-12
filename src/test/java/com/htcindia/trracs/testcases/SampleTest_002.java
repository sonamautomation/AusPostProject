package com.htcindia.trracs.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.htcindia.trracs.helpers.MyException;
import com.htcindia.trracs.listener.MyListener;

public class SampleTest_002 extends MyListener {

	@Test
	public void TC_004() throws MyException, IOException, InterruptedException {
		getBrowser().get("https://selenium.dev/");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}
	
	@Test
	public void TC_005() throws MyException, IOException, InterruptedException {
		getBrowser().get("https://www.seleniumeasy.com/");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}
	
	@Test
	public void TC_006() throws MyException, IOException, InterruptedException {
		getBrowser().get("https://www.guru99.com/selenium-tutorial.html");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}

}