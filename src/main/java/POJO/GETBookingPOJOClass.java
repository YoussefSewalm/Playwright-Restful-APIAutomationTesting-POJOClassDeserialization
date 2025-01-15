package POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder   


public class GETBookingPOJOClass {
	
       private String firstname;
       private String lastname;
       private int totalprice;  
       private Boolean depositpaid;
       BookingDatesPOJOClass bookingdates;
       private String additionalneeds;

}


