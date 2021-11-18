package photo_library.app;

import javafx.application.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photo_library.view.*;
import photo_library.app.User;
/**
 * Main Driver class to run the application and store user data through serialization.
 * @author Ali Khan
 * @author Owen Morris
 *
 */
public class PhotoLibrary extends Application implements Serializable{
	private static final long serialVersionUID = 1L;
	public static ArrayList<User> users;
	public static User currentUser;
	
	/**
	 * begins running the application window
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo_library/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		loginController photoController = 
				loader.getController();
		
		
		photoController.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	/**
	 * loads in user data and begins starting the window application
	 * @param args
	 * @throws Exception any exception will be thrown
	 */
	public static void main(String[] args) throws Exception {
		users = new ArrayList<User>();
		
		ArrayList<User> temp = retrieveUsers();
		if(temp != null) {
			users = temp;
		}
		launch(args);
		
//		User stock = new User("stock");
//		users.add(stock);
//		Album stockAlbum = new Album("stock");
//		stock.getAlbums().add(stockAlbum);
//		Photo donutPhoto = new Photo("./data/cat.jpg");
//		donutPhoto.setCaption("Cat");
//		stockAlbum.addPhoto(donutPhoto);
//		Photo mcQueenPhoto = new Photo("./data/dog.png");
//		mcQueenPhoto.setCaption("dog");
//		stockAlbum.addPhoto(mcQueenPhoto);
//		Photo nemoPhoto = new Photo("./data/elmo.jpg");
//		nemoPhoto.setCaption("elmo");
//		stockAlbum.addPhoto(nemoPhoto);
//		
		storeUsers(users);

	}
	
	/**
	 * Stores the user information into a serialized file using objects
	 * @param users list of all users in application
	 */
	public static void storeUsers(ArrayList<User> users) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
			output.writeObject(users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Reads in the serialized data from the serialized file
	 * @return list of all users with their albums and photo
	 * @throws IOException input error stream
	 * @throws ClassNotFoundException object can't be made correctly based on data
	 */
	public static ArrayList<User> retrieveUsers() throws IOException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data"+ File.separator + "users.dat"));
		ArrayList<User >temp_users = (ArrayList<User>)ois.readObject();
			return temp_users;
	}
	/**
	 * gets the user
	 * @param name name of user
	 * @return User object
	 */
	public static User getUser(String name) {
		for(User u:users) {
			if(u.getUser().equals(name)) {
				return u;
			}
		}
		return null;
	}

}