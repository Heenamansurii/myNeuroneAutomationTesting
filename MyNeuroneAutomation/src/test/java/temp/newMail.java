package temp;

import java.util.Date;

public class newMail {

	public static void main(String[] args) {
         Date date=new Date();
         String dateString= date.toString();
        String noSpace=dateString.replaceAll("\\s","");
         String noCollon=noSpace.replaceAll("\\:","");
         String addMail=noCollon+"gamil.com";
         
    	System.out.print(addMail);	 
	}

}
