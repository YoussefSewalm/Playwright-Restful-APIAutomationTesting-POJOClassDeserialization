package POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder   

public class TokenPOJOClass {
	
       private String token;
       private String username;
       private String password;  

}
