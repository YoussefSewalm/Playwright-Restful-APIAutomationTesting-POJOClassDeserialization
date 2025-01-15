package POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder   


public class ResponseUserPOJOClass {
	
	   private int bookingid;
	   GETBookingPOJOClass booking;
}


