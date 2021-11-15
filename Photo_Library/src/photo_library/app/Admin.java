package photo_library.app;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Admin {
	ArrayList<User> users = new ArrayList<User>();
	
	public Admin() throws Exception {
		File user_file = new File("src/users.txt");
		if(user_file.exists()) {
			System.out.println("creating user list");
			Scanner sc = new Scanner(user_file);
			while(sc.hasNextLine()) {
				String temp = sc.nextLine();
				User user = new User(temp);
				users.add(user);
			}
			sc.close();
		}
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public boolean deleteUser(String user) throws Exception {
		User temp = null;
		for(User u: users) {
			if(u.getUser().equals(user)) {
				temp = u;
				break;
			}
		}
		if(temp != null) {
			users.remove(temp);
			updateUsers();
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean addUser(String user) throws Exception {
		User temp = new User(user);
		users.add(temp);
		updateUsers();
		return true;
	}
	
	private void updateUsers() throws Exception {
		PrintWriter writer = new PrintWriter(new FileWriter("src/users.txt"));
		for(User u: users) {
			writer.println(u.getUser());
		}
		writer.close();
	}
}
