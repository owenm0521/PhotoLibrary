package photos.app;

import photos.view.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class which contains main method and sets up Display and Serializable
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class Photos extends Application implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * Initializes directory
	 */
	public static final String storeDir = "data";
	/**
	 * Initializes stored file
	 */
	public static final String storeFile = "users.dat";
	/**
	 * Initializes list of users in the application
	 */
	public static ArrayList<User> applicationUsers;
	/**
	 * Current user
	 */
	public static User activeUser;
	/**
	 * Find user in list of users in the application
	 * @param username The user name that we are searching for in the list.
	 * @return User Returns user object with inputed user name.
	 */
	public static User findUser(String username) {
		for (int i = 0; i<applicationUsers.size(); i++) {
			if(applicationUsers.get(i).getName().equals(username)){
				return applicationUsers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/loginPage.fxml"));
		VBox root = (VBox)loader.load();
		LoginPageController loginPage = loader.getController();
		loginPage.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	
	/**
	 * Initialize list of users in the application and handles serializable
	 * @param args User input from terminal.
	 * @throws Exception Exception thrown if file for serialization is not found.
	 */
	public static void main(String[] args) throws Exception{
		applicationUsers = new ArrayList<User>();
		//applicationUsers.add(new User("name"));
		
		
		ArrayList<User>  p = Photos.readApp();
		if(p!=null) {
			applicationUsers = p;
		}
		
		
		
//		User stock = new User("stock");
//		applicationUsers.add(stock);
//		Album stockAlbum = new Album("stock");
//		stock.getUserAlbums().add(stockAlbum);
//		Photo donutPhoto = new Photo("./data/donut.png");
//		donutPhoto.setCaption("Donut");
//		stockAlbum.addImageToAlbum(donutPhoto);
//		Photo mcQueenPhoto = new Photo("./data/mcQueen.png");
//		mcQueenPhoto.setCaption("Lightning McQueen");
//		stockAlbum.addImageToAlbum(mcQueenPhoto);
//		Photo nemoPhoto = new Photo("./data/nemo.jpeg");
//		nemoPhoto.setCaption("Nemo");
//		stockAlbum.addImageToAlbum(nemoPhoto);
//		Photo penguinPhoto = new Photo("./data/Penguin.png");
//		penguinPhoto.setCaption("Penguin");
//		stockAlbum.addImageToAlbum(penguinPhoto);
//		Photo planktonPhoto = new Photo("./data/planketon.jpeg");
//		planktonPhoto.setCaption("Plankton");
//		stockAlbum.addImageToAlbum(planktonPhoto);
//		Photo spongebobPhoto = new Photo("./data/spongebob.jpeg");
//		spongebobPhoto.setCaption("Spongebob");
//		stockAlbum.addImageToAlbum(spongebobPhoto);
		
		
		
		
		launch(args);
		//p.applicationUsers = applicationUsers;
		

		writeApp(applicationUsers);
		
	}
	/**
	 * Writes data to the Stream
	 * @param applicationUser List of users in application.
	 * @throws IOException Exception thrown if file for serialization is not found.
	 */
	public static void writeApp(ArrayList<User> applicationUser) throws IOException {//save to Disk write object to it

		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(applicationUser);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Reads data from the Stream
	 * @return ArrayList List of users that were read
	 * @throws IOException Exception thrown if file for serialization is not found.
	 * @throws ClassNotFoundException Exception thrown if casting is done improperly
	 */
	public static ArrayList<User> readApp() throws IOException, ClassNotFoundException {
	
		ObjectInputStream ois = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		ArrayList<User> p = (ArrayList<User>)ois.readObject();
		return p;

	}
}
