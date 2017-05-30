@Test
Feature: To check the functionality of Login Authentication

Background: 
Given Get test data from excel sheet

@AddTaskWithIncident
Scenario Outline: Print
	When   Print "<Feature>","<FeatureDataRow>" 
	
	Examples: 
		| Feature	      | FeatureDataRow |
		| TC1        | 1	  		   |