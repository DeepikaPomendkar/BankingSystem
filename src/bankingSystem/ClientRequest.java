package bankingSystem;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.*;
import java.util.Scanner;

public class ClientRequest {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
      

        try{
            Search access = (Search)Naming.lookup("rmi://localhost:1900"+"/pikachu");

            int choice =0;
            while(choice!=-1){

                String answer;
                System.out.println("Enter "+"\n"+"1:Bank Account Details"+"\n"+"2:Withdraw"+"\n"+"3:Deposit");
                choice = sc.nextInt();
                if (choice==1){
                    System.out.println("Enter your Account Number");

                    String accNo = sc.next();
                    answer = access.queryAccount(accNo);
                    System.out.println(answer);
                }
                else if(choice ==2){
                    System.out.println("Enter your Account Number");
                    String accNo = sc.next();
                    System.out.println("Enter amount to Withdraw");
                    float amount = sc.nextFloat();
                    answer = access.withdraw(accNo,amount);
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

        catch (Exception ae){
            System.out.println(ae);
        }
    }
}
