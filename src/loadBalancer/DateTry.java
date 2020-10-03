package loadBalancer;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.Date; 
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
public class DateTry {

	public static void main(String[] args) {
	    String url ="jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
		String user ="root";
		String pass ="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateString[] = new String[2];
		String date1String ="";
		String date2String ="";
		
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Statement stmt=con.createStatement();  
			String query = "SELECT * FROM StudentData";
			ResultSet rs=stmt.executeQuery(query);  
			int i=0;
			while (rs.next()) {
				
//				System.out.println(rs.getString("uid")+rs.getString("name")+rs.getString("dateOfJoining"));
				dateString[i]=rs.getString("dateOfJoining");
				i++;                     
				                    
				}
			Date date1 =sdf.parse(dateString[0]) ;
			Date date2 = sdf.parse(dateString[1]);
			
			long difference_In_Time =  date1.getTime() - date2.getTime() ; 
			long difference_In_Seconds = (difference_In_Time/ 1000) % 60; 
			System.out.println(difference_In_Time);
			con.close(); 
			
			
			
			
			LocalDateTime myDateObj = LocalDateTime.now();
		    System.out.println("Before formatting: " + myDateObj);
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		    String formattedDate = myDateObj.format(myFormatObj);
		    System.out.println("After formatting: " + formattedDate);
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		

	}

}
