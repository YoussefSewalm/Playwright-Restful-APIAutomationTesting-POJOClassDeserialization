package TestBase;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import POJO.TokenPOJOClass;
import PlayWrightFactory.InitializePlaywright;

public class GeneratingToken {
	
	public APIRequestContext requestContext;
	public APIResponse apipostresponse;
	public RequestOptions requestOptions;
    public TokenPOJOClass Token_Data;
    public APIResponse apiputresponse;
    public APIResponse apigetresponse;
    public APIResponse apigetupdateresponse;
    public APIResponse apideleteresponse;
    public APIResponse apigetresponserandom;
	 
@BeforeClass
public void StartSetup() throws IOException
{
	/*
	byte [] fileBytes = null;  //h3ml initialize ll array bytes
    File file = new File(System.getProperty("user.dir")+"/src/test/java/resources/TokenData.json");
    fileBytes = Files.readAllBytes(file.toPath());
    */
	TokenPOJOClass tokendata = TokenPOJOClass.builder()
            .username("admin")
            .password("password123")
            .token("null").build();
	requestOptions = RequestOptions.create();
	requestContext = InitializePlaywright.InitiatePlayWrightAPI();
	apipostresponse = requestContext.post("https://restful-booker.herokuapp.com/auth",requestOptions
			       .setData(tokendata)
			       .setHeader("Content-Type","application/json"));
	String responseText = apipostresponse.text();
	ObjectMapper objectmapper = new ObjectMapper();

	//Deserialization (Converting JSON/Text to POJO)
	Token_Data = objectmapper.readValue(responseText,TokenPOJOClass.class);
}


@AfterClass
public void EndSession()
{
	requestContext.dispose();
}

}