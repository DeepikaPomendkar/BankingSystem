package bankingSystem;
import java.rmi.*;
import java.rmi.registry.*;
public class SearchServer {

    public static void main(String args[]){
        System.out.println("The Serve has started");
        try {
            ///Create the object of the interface

            Search obj = new SearchQuery();

            //rmi registry within sever
            //port number 1900
         
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900"+"/pikachu",obj);

        }
        catch (Exception ae){
            System.out.println(ae);
        }

    }
}
