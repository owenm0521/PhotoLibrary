package photo_library.app;


import java.util.HashMap;
import java.io.File;
import java.util.Scanner;


public class User {
	private String username;
	private HashMap<String, String> albums;
	public User(String username) throws Exception {
		this.username = username;
		File file = new File("src/"+username+".txt");
		if(file.exists()) {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String temp = sc.nextLine();
				String name = temp.split(" ")[0];
				String loc = temp.split(" ")[1];
				addAlbum(name, loc);
			}
			sc.close();
		}
		else {
			System.out.println("Created new file");
			file.createNewFile();
		}
	}
	
	public String getUser(){
		return username;
	}
	
	public void addAlbum(String name, String loc){
		albums.put(name, loc);
	}
	
	public  HashMap<String, String> getAlbums() {
		return albums;
	}
}
