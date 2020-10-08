package bankingSystem;
import java.sql.*;
import bankingSystem.Search;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;


////For date
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.Date; 
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.time.Instant;
import java.util.ArrayList;

//C:\Program Files\Java\jdk-14.0.1\bin\javaw.exe -Dfile.encoding=Cp1252 -classpath "C:\Users\DeepiakP\eclipse-work-space\bankingSystem\bin;D:\mysql-connector-java-8.0.21\mysql-connector-java-8.0.21\mysql-connector-java-8.0.21.jar" bankingSystem.SearchServer
public class SearchQuery extends UnicastRemoteObject implements Search {
	private static Integer position = 1;
	private static Integer limit = 0;

    String url ="jdbc:mysql://localhost:3306/banking?serverTimezone=UTC";
	String user ="root";
	String pass ="";
	
	String url1 ="jdbc:mysql://localhost:3306/bankingbackup?serverTimezone=UTC";
	
	
	ArrayList<String> arrayOfCurrentUsers = new ArrayList<String>();
	
   

    SearchQuery() throws RemoteException{
        super();
    }


    public String queryAccount(String accNo) throws RemoteException{
        System.out.println("Client is quering the bank account .......");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     
        String dateString[] = new String[2];
        try {
        	
        	
        	
        	
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Connection con1 =DriverManager.getConnection(url1,user,pass);
			
			
			Statement stmt=con.createStatement();  
			Statement stmt1=con1.createStatement();  
			
			
			String query = "SELECT balance,lastUpdated FROM customers WHERE custAccNo ="+ accNo;
			
			ResultSet rs=stmt.executeQuery(query);  
			ResultSet rs1=stmt1.executeQuery(query); 
			
			float returnVal =0;
			float returnVal1 =0;
			while (rs.next()) {
				String tempVal = rs.getString("balance");
				   returnVal = Float.valueOf(tempVal);
				   dateString[0]=rs.getString("lastUpdated");
				                      
				                    
				}
			while (rs1.next()) {
				String tempVal = rs1.getString("balance");
				   returnVal1 = Float.valueOf(tempVal);
				   dateString[1]=rs1.getString("lastUpdated");
				                    
				}
			if(returnVal ==returnVal1) {
				return "Your Account Balance is:"+ String.valueOf(returnVal);
			}
			else {
				System.out.println("---****-----*****---One Database Server Down---****-----*****---");
				System.out.println("");
				System.out.println("---****-----*****---Working on recovery---****-----*****---");
				Date date1 =sdf.parse(dateString[0]) ;
				Date date2 = sdf.parse(dateString[1]);
				
				long difference_In_Time =  date1.getTime() - date2.getTime() ; 
				 
				System.out.println("Time Difference in updation:"+difference_In_Time);
				
				if(difference_In_Time>=0) {
					returnVal = returnVal; 
				}
				/////date 2 small
				else if (difference_In_Time<0) {
					returnVal = returnVal1;
				}
				return "Your Account Balance is:"+ String.valueOf(returnVal);
				
			}
			
			
			
		}
        

		catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}
        
        
    }
    public String deposit(String accNo,float amount) throws RemoteException{
        System.out.println("Client is depositing in his bank account .......");
        
        LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String dateString[] = new String[2];
	    
	    
	    System.out.println("After formatting: " + formattedDate);
        try {
        	

        	if(arrayOfCurrentUsers.contains(accNo)) {
        		return "Your account is being accessed currently.Come back later.";
        	}
        	else {
        		arrayOfCurrentUsers.add(accNo);
        	}
        	Thread.sleep(6000);  ///Add delay because in real life situation there is a delay sometimes 
        	
        	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Connection con1 =DriverManager.getConnection(url1,user,pass);
			
			
			Statement stmt=con.createStatement();  
			Statement stmt1=con1.createStatement();  
			
			
			String query = "SELECT balance,lastUpdated FROM customers WHERE custAccNo ="+ accNo;
			
			ResultSet rs=stmt.executeQuery(query);  
			ResultSet rs1=stmt1.executeQuery(query); 
			
			float returnVal =0;
			float returnVal1 =0;
			while (rs.next()) {
				String tempVal = rs.getString("balance");
				   returnVal = Float.valueOf(tempVal);
				   dateString[0]=rs.getString("lastUpdated");
				                      
				                    
				}
			while (rs1.next()) {
				String tempVal = rs1.getString("balance");
				   returnVal1 = Float.valueOf(tempVal);
				   dateString[1]=rs1.getString("lastUpdated");            
				                    
				}
			if(returnVal ==returnVal1) {
				returnVal = returnVal + amount;	
			}
			else {
				
				
				System.out.println("---****-----*****---One Database Server Down---****-----*****---");
				System.out.println("");
				System.out.println("---****-----*****---Working on recovery---****-----*****---");
				
				Date date1 =sdf.parse(dateString[0]) ;
				Date date2 = sdf.parse(dateString[1]);
				
				long difference_In_Time =  date1.getTime() - date2.getTime() ; 
				 
				System.out.println("Time Difference in updation:"+difference_In_Time);
				
				///date 1 small
				if(difference_In_Time>=0) {
					returnVal = returnVal + amount;
				}
				/////date 2 small
				else if (difference_In_Time<0) {
					returnVal = returnVal1 + amount;
				}
				
				
				
			}
			
			query = "UPDATE customers SET lastUpdated ='"+formattedDate+"', balance ="+returnVal+" WHERE custAccNo='"+accNo+"';";
//			System.out.println(query);
			stmt.executeUpdate(query);  
			stmt1.executeUpdate(query);  
			
			
			con.close(); 
			con1.close(); 
			arrayOfCurrentUsers.remove(accNo);
			return  "---****-----*****---Money Deposited---****-----*****---";
			
		}
        catch(InterruptedException e1) {
			Thread.currentThread().interrupt();
			return e1.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}

        
        
        
     
    }
    public String withdraw(String accNo,float amount) throws RemoteException{
    	System.out.println("Client is depositing in his bank account .......");
    	
    	LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String dateString[] = new String[2];
	    
        try {
        	
        	if(arrayOfCurrentUsers.contains(accNo)) {
        		return "Your account is being accessed currently.Come back later.";
        	}
        	else {
        		arrayOfCurrentUsers.add(accNo);
        	}
        	Thread.sleep(6000);  ///Add delay because in real life situation there is a delay sometimes 
        	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Connection con1 =DriverManager.getConnection(url1,user,pass);
			
			Statement stmt=con.createStatement();  
			Statement stmt1=con1.createStatement();  
			
			String query = "SELECT balance,lastUpdated FROM customers WHERE custAccNo ="+ accNo;
			ResultSet rs=stmt.executeQuery(query);  
			ResultSet rs1=stmt1.executeQuery(query);  
			
			float returnVal =0;
			float returnVal1 =0;
			while (rs.next()) {
				String tempVal = rs.getString("balance");
				   returnVal = Float.valueOf(tempVal);
				   dateString[0]=rs.getString("lastUpdated"); 
				                      
				                    
			}
			while (rs1.next()) {
				String tempVal = rs1.getString("balance");
				   returnVal1 = Float.valueOf(tempVal);
				   dateString[1]=rs1.getString("lastUpdated"); 
				                      
				                    
			}
			if(returnVal == returnVal1) {
				if(returnVal>=amount) {
					
					returnVal = returnVal - amount;
				}
				else {
					String ret = "Not enough Money In your Account.Your current Balance is:"+Float.toString(returnVal);
					return ret;
				}
				

			}
			else {
				
				
				System.out.println("---****-----*****---One Database Server Down---****-----*****---");
				System.out.println("");
				System.out.println("---****-----*****---Working on recovery---****-----*****---");
				
				Date date1 =sdf.parse(dateString[0]) ;
				Date date2 = sdf.parse(dateString[1]);
				
				long difference_In_Time =  date1.getTime() - date2.getTime() ; 
				 
				System.out.println("Time Difference in updation:"+difference_In_Time);
				
				///date 1 small
				if(difference_In_Time>=0) {
					returnVal = returnVal - amount;
				}
				/////date 2 small
				else if (difference_In_Time<0) {
					returnVal = returnVal1 - amount;
				}
				
				
				
			}
			
			query = "UPDATE customers SET lastUpdated ='"+formattedDate+"', balance ="+returnVal+" WHERE custAccNo='"+accNo+"';";
//			System.out.println(query);
			stmt.executeUpdate(query);  
			stmt1.executeUpdate(query);  
			
			
			con.close(); 
			con1.close(); 
			arrayOfCurrentUsers.remove(accNo);
			return  "---****-----*****---Money Withdrawn---****-----*****---";

			
		}
        catch(InterruptedException e1) {
			Thread.currentThread().interrupt();
			return e1.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}

        
    	}
    @Override
	public String getFreeServer() throws RemoteException {
		// TODO Auto-generated method stub
		Map map=new HashMap();  

	    map.put(1,"1901");  
	    map.put(2,"1902");  
	    map.put(3,"1903");  
	    map.put(4,"1904");  
	    
 
		 if (limit>=10) {
			 if (position > 4) {
	         	System.out.println(position);
	             position = 1;
	             limit = 0;
	         }
			 else {
	         
				 position++;
			 }
		 }
		 limit = limit +1;
            
        
	    System.out.println(position);
	    
	    String target =(String) map.get(position);
        return target;
	}
    public long getSystemTime() {  
        // Calculating Epoch time on server
     	
        long time = Instant.now().toEpochMilli();
        System.out.println("This is the current time on the server, "+ time);
        return time;  
     }  
        
     

    }





