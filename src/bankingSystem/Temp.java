package bankingSystem;

public class Temp {

	public static void main(String[] args) {

		try {
			Thread.sleep(2000);
			
			System.out.println("Hi");
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
