package Tests;

import static JsonDataDriven.JsonData.getJsonData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.BookingDatesPOJOClass;
import POJO.GETBookingPOJOClass;
import POJO.ResponseUserPOJOClass;
import TestBase.GeneratingToken;


public class CreatingUserThenUpdatingThenDeleting extends GeneratingToken{
	
			                                   
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
 		
		GETBookingPOJOClass BookingUserDataBodyReq = GETBookingPOJOClass.builder()
				                                      .firstname(input.get("firstname"))
				                                      .lastname(input.get("lastnamebeforeupdate"))
				                                      .totalprice(Integer.parseInt(input.get("totalpricebeforeupdate")))
				                                      .depositpaid(Boolean.parseBoolean(input.get("Isdeposidpaid")))
				                                      .bookingdates(BookingDatesBody)
						                      .additionalneeds(input.get("additionalneeds"))
				                                      .build();
	                                
	    System.out.println("My Token is :"+Token_Data.getToken());

	    //Creating User
	   apipostresponse = requestContext.post(input.get("Url")+"/booking", requestOptions
				                        .setHeader("Content-Type", "application/json")
				                        .setHeader("Accept", "application/json")
				                        .setData(BookingUserDataBodyReq)	                        
				                    );
		
	   ObjectMapper objectmapper = new ObjectMapper();
	   String postresponseText = apipostresponse.text();
	   System.out.println(postresponseText);
	   ResponseUserPOJOClass DataAfterCreatingUser = objectmapper.readValue(postresponseText,ResponseUserPOJOClass.class);
	   int IdBooking = DataAfterCreatingUser.getBookingid();
	   System.out.println(IdBooking);
	   
           Assert.assertEquals(apipostresponse.status() , 200);
	   Assert.assertEquals(DataAfterCreatingUser.getBooking().getFirstname(), BookingUserDataBodyReq.getFirstname());
	   
	   //Updating User
		GETBookingPOJOClass UpdatingUserReqBody= GETBookingPOJOClass.builder()
                .firstname(input.get("firstname"))
                .lastname(input.get("lastnameafterupdate"))
                .totalprice(Integer.parseInt(input.get("totalpriceafterupdate")))
                .depositpaid(Boolean.parseBoolean(input.get("Isdeposidpaid")))
                .bookingdates(BookingDatesBody)
                .additionalneeds(input.get("additionalneeds"))
                .build();
		apiputresponse = requestContext.put(input.get("Url")+"/booking/"+String.valueOf(IdBooking), requestOptions
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
         
     	//Get Updated User
		apigetresponse = requestContext.get(input.get("Url")+"/booking/"+String.valueOf(IdBooking), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
				); 
		
		   String GETresponseText = apigetresponse.text();
		   System.out.println(GETresponseText);
		   
	  GETBookingPOJOClass DataAfterGettingUser = objectmapper.readValue(GETresponseText,GETBookingPOJOClass.class);
	  
         Assert.assertEquals(apigetresponse.status() , 200);
         Assert.assertEquals(DataAfterGettingUser.getLastname(), UpdatingUserReqBody.getLastname());
         Assert.assertEquals(DataAfterGettingUser.getTotalprice(), UpdatingUserReqBody.getTotalprice());
         
      	//Delete User
 		apideleteresponse = requestContext.delete(input.get("Url")+"/booking/"+String.valueOf(IdBooking), requestOptions
                 .setHeader("Cookie", "token="+Token_Data.getToken())
 				); 
 		
               String DELETEresponseText = apideleteresponse.text();
 	       System.out.println(DELETEresponseText);
 	       Assert.assertEquals(apideleteresponse.status() , 201);
 	       Assert.assertEquals(apideleteresponse.statusText(), "Created");
	}
	

}
