package com.test;

import java.io.IOException;

import com.java.ExcelReader;

import cucumber.api.java.en.When;

public class ExcelTest {

	@When("^Print \"([^\"]*)\",\"([^\"]*)\"$")
	public void test(String a, int f) throws IOException{
		System.out.println(ExcelReader.testData);
	}
}
