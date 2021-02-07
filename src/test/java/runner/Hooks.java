package runner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.sql.SQLException;

public class Hooks {

	@Before
	public void beforeScenario(Scenario scenario){
		System.out.println(" ******************* Before Hooks ******************** ");

		System.out.println("Started scenario "+scenario.getName());

	}

	@After
	public void afterScenario(Scenario scenario) throws SQLException {
		System.out.println(" ****************** After Hooks ******************** ");

		System.out.println("Finished scenario "+scenario.getName());
    }
}
