package bankingSystem;
import java.time.Instant;
import java.time.Clock;
import java.time.Duration;

import java.io.IOException;
import java.util.*;  
import java.io.InputStream;
import java.rmi.*;
import java.util.Scanner;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MultipleClients {

	public static void main(String[] args) {
		int i = 42;
		for(int j=0;j<=42;j++) {
			
			String position ="";
	        String urlName ="";
	        String answer;
			
			try {
				Search access1 = (Search)Naming.lookup("rmi://localhost:1900"+"/pikachu");
				position = access1.getFreeServer();
	            

	            urlName = "/pikachu";
	            
	            String url = "rmi://localhost:"+position;
                System.out.println("Server at port "+url);
                Search access = (Search)Naming.lookup(url+urlName);
                
                answer = access.deposit("2017130044",1);
                System.out.println(answer);
				
				
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
		

	}

}
