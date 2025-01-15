package Tests;

import static JsonDataDriven.JsonData.getJsonData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.BookingDatesPOJOClass;
import POJO.BookingIDsPOJOClass;
import POJO.GETBookingPOJOClass;
import TestBase.GeneratingToken;

public class GettingAndUpdatingAndDeletingRandomID extends GeneratingToken {
	
	@DataProvider
	public Object[][] UpdateBooking() throws IOException
	{
	    String jsonFilePath_1 = System.getProperty("user.dir") + "//src//test//java//resources//GeneralProperties.json";

	    List<HashMap<String,String>> data_1 = getJsonData(jsonFilePath_1);

	    return new Object[][] {   { data_1.get(0) }  };
	}
	
	@Test(dataProvider="UpdateBooking")
	public void GetBooking(HashMap<String,String> input) throws IOException
	{
		BookingDatesPOJOClass BookingDatesBody = BookingDatesPOJOClass.builder()
				                                       .checkin(input.get("checkindate"))
				                                       .checkout(input.get("checkoutdate")).build();
                                
	    System.out.println("My Token is :"+Token_Data.getToken());
	    
	    //Getting all IDs
	   apigetresponse = requestContext.get(input.get("Url")+"/booking");
	   ObjectMapper objectmapper = new ObjectMapper();
	   String getresponseText = apigetresponse.text();
	   System.out.println(getresponseText);
       List<BookingIDsPOJOClass> user_Ids = objectmapper.readValue(getresponseText, new TypeReference<List<BookingIDsPOJOClass>>() {});
	
       int available_Id = Integer.parseInt(user_Ids.getFirst().getBookingid());
       Assert.assertEquals(apigetresponse.status() , 200);
       
       //Getting Random User
	   apigetresponserandom = requestContext.get(input.get("Url")+"/booking/"+available_Id);
       Assert.assertEquals(apigetresponserandom.status() , 200);
	   String getresponseText_random = apigetresponserandom.text();
	   System.out.println(getresponseText_random);
    
       //Updating Random User
		GETBookingPOJOClass UpdatingUserReqBody= GETBookingPOJOClass.builder()
                .firstname(input.get("firstname"))
                .lastname(input.get("lastnameafterupdate"))
                .totalprice(Integer.parseInt(input.get("totalpriceafterupdate")))
                .depositpaid(Boolean.parseBoolean(input.get("Isdeposidpaid")))
                .bookingdates(BookingDatesBody)
                .additionalneeds(input.get("additionalneeds"))
                .build();
		apiputresponse = requestContext.put(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Cookie", "token="+Token_Data.getToken())
                .setData(UpdatingUserReqBody)               
				); 
		
		   String PUTresponseText = apiputresponse.text();
		   System.out.println(PUTresponseText);
		   
	  GETBookingPOJOClass DataAfterUpdatingUser = objectmapper.readValue(PUTresponseText,GETBookingPOJOClass.class);
	  
         Assert.assertEquals(apiputresponse.status() , 200);
         Assert.assertEquals(DataAfterUpdatingUser.getLastname(), UpdatingUserReqBody.getLastname());
         Assert.assertEquals(DataAfterUpdatingUser.getTotalprice(), UpdatingUserReqBody.getTotalprice());
         
         //Getting Updated User
 		apigetupdateresponse = requestContext.get(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Cookie", "token="+Token_Data.getToken())
				); 
		
		   String GETupdateresponseText = apigetupdateresponse.text();
		   System.out.println(GETupdateresponseText);
		   
	  GETBookingPOJOClass DataAfterGettingUser = objectmapper.readValue(GETupdateresponseText,GETBookingPOJOClass.class);
	  
         Assert.assertEquals(apigetupdateresponse.status() , 200);
         Assert.assertEquals(DataAfterGettingUser.getLastname(), UpdatingUserReqBody.getLastname());
         Assert.assertEquals(DataAfterGettingUser.getTotalprice(), UpdatingUserReqBody.getTotalprice());
	   
         //Deleting Updated User
  		apideleteresponse = requestContext.delete(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Cookie", "token="+Token_Data.getToken())
				); 
		
		   String DELETEresponseText = apideleteresponse.text();
		   System.out.println(DELETEresponseText);
	       Assert.assertEquals(apideleteresponse.status() , 201);
	       Assert.assertEquals(apideleteresponse.statusText(), "Created");
	}

}
