package com.htcindia.trracs.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.htcindia.trracs.helpers.MyException;
import com.htcindia.trracs.listener.MyListener;

public class SampleTest_001 extends MyListener {

	@Test
	public void TC_001() throws MyException, IOException, InterruptedException {
		getBrowser().get("http://demo.automationtesting.in/");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}
	
	@Test
	public void TC_002() throws MyException, IOException, InterruptedException {
		getBrowser().get("http://www.newtours.demoaut.com/");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}
	
	@Test
	public void TC_003() throws MyException, IOException, InterruptedException {
		getBrowser().get("https://www.way2automation.com/demo.html");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}

}