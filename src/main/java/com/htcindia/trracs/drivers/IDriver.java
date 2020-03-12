package com.htcindia.trracs.drivers;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.htcindia.trracs.helpers.MyException;

public interface IDriver {

	WebDriver launch(String browserName) throws MyException, MalformedURLException;

}