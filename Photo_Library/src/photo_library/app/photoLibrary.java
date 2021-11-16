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

public class photoLibrary extends Application implements Serializable{
	private static final long serialVersionUID = 1L;
	public static ArrayList<User> users;
	public static User currentUser;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo_library/view/login.fxml"));
		System.out.println("starting to load");
		AnchorPane root = (AnchorPane)loader.load();
		System.out.println("loaded pane");
		loginController photoController = 
				loader.getController();
		
		
		photoController.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
	
	public static void storeUsers(ArrayList<User> users) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("pictures/users.dat"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<User> retrieveUsers() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pictures/users.dat"));
			ArrayList<User> temp_users = (ArrayList<User>)ois.readObject();
			return temp_users;
	}

}