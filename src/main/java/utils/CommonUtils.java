package utils;

import java.util.Date;

public class CommonUtils {
	
	public static String generateBrandNewMail() {
		return new Date().toString().replaceAll("\\s","").replaceAll("\\:","")+"@gamil.com";
        
		 
}

}
