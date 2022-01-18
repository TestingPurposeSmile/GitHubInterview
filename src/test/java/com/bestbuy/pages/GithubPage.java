package com.bestbuy.pages;

import com.bestbuy.utilities.BrowserActions;
import com.bestbuy.utilities.ReadProperties;
import com.bestbuy.utilities.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class GithubPage extends BrowserActions{

	@FindBy(css="a[data-lid='hdr_lgo'] img[class='logo']")
	WebElement logo;

	@FindBy (xpath="//a[@class='menu-item' and contains(text(), 'Users')]")
	WebElement userLink;

	@FindBy(name ="q")
	WebElement userSearch;

	@FindBy(id ="repo-stars-counter-star")
	WebElement starCountUi;

	@FindBy (xpath="(//*[@class=\"pinned-item-list-item-content\"]//a)[1]")
	WebElement firstRepo;

	@FindBy (xpath="(//a[@class='mr-1'])[1]")
	WebElement firstUser;


	@FindBy (xpath="//button[@data-track='Close']")
	WebElement closePopUp;

	private static final OkHttpClient client = new OkHttpClient.Builder().
			connectTimeout(180, TimeUnit.SECONDS)
			.readTimeout(180, TimeUnit.SECONDS)
			.writeTimeout(180, TimeUnit.SECONDS).build();

	private JSONParser jsonParser;
	private JSONObject jsonObject;

	public GithubPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	BrowserActions actions = new BrowserActions();
	WebDriver driver = actions.getInstance();
	public static Properties envProperties = ReadProperties.getPropInstance().testProperties;
	String url = envProperties.getProperty("app.url");



	public GithubPage navigateToUserSearchPage(WebDriver driver, String product) throws Exception {
		Utils.waitForPageLoad(driver);
		BrowserActions.clickOnButton(userSearch, driver, "clicked the search user box");
		BrowserActions.typeOnTextField(userSearch, product, driver, "Enter the user name");
		BrowserActions.pressEnter(driver, userSearch, "Enter key pressed");
		return new GithubPage(driver);
	}

	public void clickOnUserSearch(WebDriver driver) throws Exception {
		clickOnButton(userLink,driver,"click on the user link in the repo page");
	}

	public void clickOnSpecificUser(WebDriver driver) throws Exception {
		clickOnButton(firstUser,driver,"click on the user matching user");
	}

	public void clickOnFirstRepo(WebDriver driver) throws Exception {
		clickOnButton(firstRepo,driver,"click on the first repo of the user");
	}

	public String getAttributeofCountUIStar(WebDriver driver) throws Exception {
		 return  getTextFromAttribute(driver,starCountUi,"title","Get the count of the star using the attribute value");


	}



	public static Response sendGetRequest(String uri, Map<String, String> headers) {
		Request request = addHeaders(headers).url(uri).build();
		Response res = null;

		try {
			res = client.newCall(request).execute();
		} catch (IOException var7) {
			var7.printStackTrace();
		}

		return res;
	}

	public static Request.Builder addHeaders(Map<String, String> sendHeaders) {
		Request.Builder builder = new Request.Builder();
		if (sendHeaders != null) {
			Iterator var2 = sendHeaders.keySet().iterator();

			while(var2.hasNext()) {
				String key = (String)var2.next();
				builder.addHeader(key, (String)sendHeaders.get(key));
			}
		}
		return builder;
	}
	public JSONObject convertJsonStringToJsonObject(Response jsonValue) {
		this.jsonParser = new JSONParser();

		try {
			this.jsonObject = new JSONObject(jsonValue);
		} catch (Exception var3) {


		}

		return this.jsonObject;
	}

    public static JsonNode getJsonNodeFromResponse(Response response) {
        JsonNode jsonNode = null;
        String jsonData = "";

        try {
            jsonData = response.body().string();
            jsonNode = (new ObjectMapper()).readTree(jsonData);
            return jsonNode;
        } catch (Exception var4) {
            System.out.println("failed");
            //throw new TapException(TapExceptionType.PROCESSING_FAILED, "Not able to convert response json data into json node [{}]", new Object[]{jsonData});
        }
        return jsonNode;
    }



//	public void closePopUp(WebDriver driver) throws InterruptedException {
//		Utils.waitForPageLoad(driver);
//		closePopUp.click();
//		Utils.waitForPageLoad(driver);
//	}

}
