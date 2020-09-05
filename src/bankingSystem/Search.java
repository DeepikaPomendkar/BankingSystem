package bankingSystem;

import java.rmi.*;
////https://www.geeksforgeeks.org/remote-method-invocation-in-java/#:~:text=Through%20RMI%2C%20object%20running%20in,calls%20on%20the%20server%20object.
/////This is an Remote interface which specifies the methods which can be invoked by the remote clients
public interface Search extends Remote{

    //// The method should throw this exception



    public String queryAccount(String search) throws RemoteException;

    public String withdraw(String accNo,float amount) throws RemoteException;

    public String deposit(String accNo,float amount) throws RemoteException;



}