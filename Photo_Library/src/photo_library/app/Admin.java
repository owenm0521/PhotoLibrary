package photo_library.app;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Admin {
	ArrayList<User> users;
	
	public Admin() throws Exception {
		File user_file = new File("users.txt");
		if(user_file.exists()) {
			Scanner sc = new Scanner(user_file);
			while(sc.hasNextLine()) {
				User user = new User(sc.nextLine());
				users.add(user);
			}
			sc.close();
		}
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public boolean deleteUser(String user) throws IOException {
		User temp = null;
		for(User u: users) {
			if(u.getUser().equals(user)) {
				temp = u;
				break;
			}
		}
		if(temp != null) {
			users.remove(temp);
			PrintWriter writer = new PrintWriter(new FileWriter("users.txt"));
			for(User u: users) {
				writer.println(u.getUser());
			}
			writer.close();
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean addUser(String user) throws Exception {
		User temp = null;
		for(User u: users) {
			if(u.getUser().equals(user)) {
				temp = u;
				break;
			}
		}
		if(temp != null) {
			return false;
		}
		else {
			users.add(new User(user));
			PrintWriter writer = new PrintWriter(new FileWriter("users.txt"));
			for(User u: users) {
				writer.println(u.getUser());
			}
			writer.close();
			return true;
		}
	}
}
