package bankingSystem;
import java.rmi.*;
import java.rmi.registry.*;
public class SearchServer {

    public static void main(String args[]){
    	
    ///https://stackoverflow.com/questions/49118941/how-can-i-create-multiple-servers-with-java-rmi
        System.out.println("The Serve has started");
        try {
            ///Create the object of the interface

            Search obj = new SearchQuery();
            
            //rmi registry within sever
            //port number 1900     
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900"+"/pikachu",obj);
            
            LocateRegistry.createRegistry(1901);
            Naming.rebind("rmi://localhost:1901"+"/pikachu",obj);
            
            LocateRegistry.createRegistry(1902);
            Naming.rebind("rmi://localhost:1902"+"/pikachu",obj);
            
            LocateRegistry.createRegistry(1903);
            Naming.rebind("rmi://localhost:1903"+"/pikachu",obj);
            
            
            LocateRegistry.createRegistry(1904);
            Naming.rebind("rmi://localhost:1904"+"/pikachu",obj);
            
            
            /// try and connect to children 
            
    

        }
        catch (Exception ae){
            System.out.println(ae);
        }
        
        
        

    }
}
