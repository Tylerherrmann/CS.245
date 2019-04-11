import java.util.EmptyStackException;
import java.util.Scanner;

public class BrowsingHistory {

	static Stack urlStack = new Stack();
	static String currentURL = "";
	
	public static void main(String[] args) {
		
		// Request user input and catch it in variable input.
		Scanner stdin = new Scanner(System.in);
		System.out.println("Enter a URL or \"quit\": ");
		String input = stdin.next();
		
		
		// Check if user is requesting to quit
		if(input.toLowerCase().equals("quit")) {
			return;
		// Else check if user requests to go back to earlier URL.
		} else if(input.toLowerCase().equals("back")) {
			
			if(urlStack.isEmpty() && currentURL.isEmpty()) {
				System.out.println("No URL to go back to");
				main(null);
			} else if(urlStack.isEmpty() && !currentURL.isEmpty()) {
				System.out.println("No URL to go back to");
				System.out.println("Current URL: " + currentURL);
				main(null);
			} else {
				System.out.println("Current URL: " + urlStack.pop());
				main(null);
			}
			
		} else {
			
			if(currentURL.isEmpty()) {
				currentURL = input.toString();
				System.out.println("Current Url: " + currentURL);
				main(null);
			} else {
				urlStack.push(currentURL);
				currentURL = input.toString();
				System.out.println("Current Url: " + currentURL);
				main(null);
			}
			
		}
	}

}
