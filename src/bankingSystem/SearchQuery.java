package bankingSystem;
import java.sql.*;
import bankingSystem.Search;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;


//C:\Program Files\Java\jdk-14.0.1\bin\javaw.exe -Dfile.encoding=Cp1252 -classpath "C:\Users\DeepiakP\eclipse-work-space\bankingSystem\bin;D:\mysql-connector-java-8.0.21\mysql-connector-java-8.0.21\mysql-connector-java-8.0.21.jar" bankingSystem.SearchServer
public class SearchQuery extends UnicastRemoteObject implements Search {
	private static Integer position = 0;

    String url ="jdbc:mysql://localhost:3306/banking?serverTimezone=UTC";
	String user ="root";
	String pass ="";
	
	String url1 ="jdbc:mysql://localhost:3306/bankingbackup?serverTimezone=UTC";
	
	
	
   

    SearchQuery() throws RemoteException{
        super();
    }


    public String queryAccount(String search) throws RemoteException{
        System.out.println("Client is quering the bank account .......");
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			
			
			
			
			Statement stmt=con.createStatement();  
			String query = "SELECT balance FROM customers WHERE custAccNo ="+ search;
			ResultSet rs=stmt.executeQuery(query);  
			String returnVal ="";
			while (rs.next()) {
				   returnVal = rs.getString("balance");
				                      
				                    
				}
			
			con.close(); 
			return  returnVal;
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}

        
    }
    public String deposit(String accNo,float amount) throws RemoteException{
        System.out.println("Client is depositing in his bank account .......");
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Connection con1 =DriverManager.getConnection(url1,user,pass);
			
			
			Statement stmt=con.createStatement();  
			Statement stmt1=con1.createStatement();  
			
			
			String query = "SELECT balance FROM customers WHERE custAccNo ="+ accNo;
			
			ResultSet rs=stmt.executeQuery(query);  
			ResultSet rs1=stmt1.executeQuery(query); 
			
			float returnVal =0;
			float returnVal1 =0;
			while (rs.next()) {
				String tempVal = rs.getString("balance");
				   returnVal = Float.valueOf(tempVal);
				                      
				                    
				}
			while (rs1.next()) {
				String tempVal = rs1.getString("balance");
				   returnVal1 = Float.valueOf(tempVal);
				                      
				                    
				}
			if(returnVal ==returnVal1) {
				returnVal = returnVal + amount;
				
				
				
				query = "UPDATE customers SET balance="+returnVal+" WHERE custAccNo="+accNo;
				stmt.executeUpdate(query);  
				stmt1.executeUpdate(query);  
				
				
				con.close(); 
				return  "---****-----*****---Money Deposited---****-----*****---";
			}
			else {
				return  "---****-----*****---Server Down---****-----*****---";
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}

        
        
        
     
    }
    public String withdraw(String accNo,float amount) throws RemoteException{
    	System.out.println("Client is depositing in his bank account .......");
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,pass);
			Connection con1 =DriverManager.getConnection(url1,user,pass);
			
			Statement stmt=con.createStatement();  
			Statement stmt1=con1.createStatement();  
			
			String query = "SELECT balance FROM customers WHERE custAccNo ="+ accNo;
			ResultSet rs=stmt.executeQuery(query);  
			ResultSet rs1=stmt1.executeQuery(query);  
			
			float returnVal =0;
			float returnVal1 =0;
			while (rs.next()) {
				String tempVal = rs.getString("balance");
				   returnVal = Float.valueOf(tempVal);
				                      
				                    
			}
			while (rs1.next()) {
				String tempVal = rs1.getString("balance");
				   returnVal1 = Float.valueOf(tempVal);
				                      
				                    
			}
			if(returnVal ==returnVal1) {
				if(returnVal>=amount) {
					
					returnVal = returnVal - amount;
				}
				else {
					String ret = "Not enough Money In your Account.Your current Balance is:"+Float.toString(returnVal);
					return ret;
				}
				
				
				query = "UPDATE customers SET balance="+returnVal+" WHERE custAccNo="+accNo;
				stmt.executeUpdate(query);  
				stmt1.executeUpdate(query);  
				
				
				
				con.close(); 
				con1.close(); 
				return  "---****-----*****---Money Withdrawn---****-----*****---";
			}
			else {
				return  "---****-----*****---Server Down---****-----*****---";
			}
			
			
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
	    
 
	    synchronized (position) {
            if (position > 4) {
                position = 0;
            }
            
            position++;
        }
	    String target =(String) map.get(position);
        return target;
	}
        
     

    }





