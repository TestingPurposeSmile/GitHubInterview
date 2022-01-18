package com.bestbuy.step_definitions;


import com.bestbuy.pages.GithubPage;
import com.bestbuy.utilities.BrowserActions;
import com.bestbuy.utilities.ReadProperties;
import com.bestbuy.utilities.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import cucumber.api.java.en.*;
import cucumber.api.java.en.Then;
import okhttp3.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GitPageStep {

	BrowserActions actions = new BrowserActions();
	WebDriver driver = actions.getInstance();
	GithubPage gitPage = new GithubPage(driver);
	GithubPage githubpage;

	public static Properties envProperties = ReadProperties.getPropInstance().testProperties;
	String url = envProperties.getProperty("app.url");
    String api_url = envProperties.getProperty("api.url");

	@When("^I navigate to SearchResultPage after search the (.+)$")
	public void i_navigate_pdpPager_after_the(String product) throws Exception {
		githubpage = gitPage.navigateToUserSearchPage(driver, product);
		Utils.waitForPageLoad(driver);
	}

	@Given("^I navigate to git hub application$")
	public void i_navigate_to_git_hub_application() throws Throwable {
		actions.open_url(url);
		Utils.waitForPageLoad(driver);
	}

	@Then("^I click on the user search link$")
	public void i_clcik_on_user_link_serach() throws Exception {
		gitPage.clickOnUserSearch(driver);
		Utils.waitForPageLoad(driver);
	}

	@Then("^I click on the first repo$")
	public void i_clcik_on_first_repo() throws Exception {
		gitPage.clickOnFirstRepo(driver);
		Utils.waitForPageLoad(driver);
	}

	@And("^I click on the user$")
	public void i_select_the_user() throws Exception {
		gitPage.clickOnSpecificUser(driver);
		Utils.waitForPageLoad(driver);
	}


	@And("^I validate the count of the star for ui and api response for the (.+)$")
	public void i_validate_starCount_api_ui(String repo) throws Throwable {
		String actual_UI_StartCount =gitPage.getAttributeofCountUIStar(driver).replaceAll(",","");
		Map<String, String> sendHeaders = new HashMap<String, String>();
        sendHeaders.put("content-type", "application/json; charset=utf-8");
        Response  response= gitPage.sendGetRequest(api_url+repo,sendHeaders);
		JsonNode jsonNode  = gitPage.getJsonNodeFromResponse(response);
		String api_count = jsonNode.get("stargazers_count").toString().replaceAll("\\\"", "");
		System.out.println("-----------------"+api_count+"----------"+actual_UI_StartCount);
		Assert.assertEquals("star count is matched", api_count, actual_UI_StartCount);
	}
}