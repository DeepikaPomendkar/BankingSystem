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
public class ClientRequest {

	
	
	
    public static void main(String args[]){
    	int conditionOfSystem=0;
        Scanner sc = new Scanner(System.in);
        ClientRequest c = new ClientRequest();
        String position ="";
        String urlName ="";
        Clock clientClock = Clock.systemUTC();
        long start = Instant.now().toEpochMilli();
		System.out.print("Start :");
		System.out.println(start);
        try{
        	///Master Server Load Balancer
        	try {
        		
        		
	            Search access1 = (Search)Naming.lookup("rmi://localhost:1900"+"/pikachu");
	            long serverTime = access1.getSystemTime();
	            System.out.println("Server time :"+ serverTime);
				long end = Instant.now().toEpochMilli(); 
				System.out.print("End :");
				System.out.println(end);
				// Calulate RTT
				long rtt = (end-start)/2;
				System.out.println("RTT "+ rtt);

				// Calcuate updatedTime to set the client clock with RTT delay
				long updatedTime = serverTime+rtt;
				
				// Calculate offset
				Duration diff = Duration.ofMillis(updatedTime - clientClock.instant().toEpochMilli());
				
				// Set Client clock based on offset to server time
				clientClock = clientClock.offset(clientClock, diff);
				System.out.println("\nNew Client Time "+ clientClock.instant().toEpochMilli());
				System.out.println("\n");
	            
	            position = access1.getFreeServer();
	            conditionOfSystem=1;
//	            int x = 9/0;
	            urlName = "/pikachu";
        	}
        	catch(Exception e) {
        		try {
        			Search access1 = (Search)Naming.lookup("rmi://localhost:1905"+"/charmander");
                    position = "1905";
                    conditionOfSystem=1;
                    urlName = "/charmander";
//                    int x = 9/0;
                    System.out.println("Main Server Is down backup is being used");
                    
        		}
        		catch(Exception e1) {
        			System.out.println("The System is down please Visit later");
            		conditionOfSystem=0;
        		}
        		
        	}
        	
        	if(conditionOfSystem==1) {
        		String url = "rmi://localhost:"+position;
                System.out.println("Server at port "+url);
                Search access = (Search)Naming.lookup(url+urlName);
                
                int choice =0;
                while(choice!=-1){

                    String answer;
                    System.out.println("Enter "+"\n"+"1:Bank Account Details"+"\n"+"2:Withdraw"+"\n"+"3:Deposit");
                    choice = sc.nextInt();
                    if (choice==1){
                        System.out.println("Enter your Account Number");

                        String accNo = sc.next();
                        answer = access.queryAccount(accNo); //1
                        
                        
                        
                        System.out.println(answer);
                    }
                    else if(choice ==2){
                        System.out.println("Enter your Account Number");
                        String accNo = sc.next();
                        System.out.println("Enter amount to Withdraw");
                        float amount = sc.nextFloat();
                        answer = access.withdraw(accNo,amount); //2
                        
                        System.out.println(answer);
                    }
                    else if (choice==3){
                        System.out.println("Enter your Account Number");
                        String accNo = sc.next();
                        System.out.println("Enter amount to Deposit");
                        float amount = sc.nextFloat();
                        answer = access.deposit(accNo,amount);
                        System.out.println(answer);
                    }
                    else if (choice==-1){
                        break;
                    }
                    else{
                        System.out.println("Wrong Input");
                    }

                }
        	}
        	
            //// Slave Server
            
        }

        catch (Exception ae){
            System.out.println(ae);
        }
    }
}
