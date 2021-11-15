package photo_library.view;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.io.BufferedReader;  
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import photo_library.app.*;

public class loginController extends PhotoLibController {
	ArrayList<User> users;
	
	@FXML Button login;
	@FXML TextField username;
	
	public void start(Stage PrimaryStage) throws Exception {
		Admin admin = new Admin();
		users = admin.getUsers();
		
		
	}
	public void login(ActionEvent e, Stage primaryStage) throws Exception {
		if((Button)e.getSource() == login) {
			String new_user = username.getText().trim();
			if(new_user.equals("admin")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/photoLibrary/view/admin.fxml"));
				AdminController photoController = 
						loader.getController();
				photoController.start(primaryStage);
				AnchorPane root = (AnchorPane)loader.load();

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Admin Console");
				primaryStage.setResizable(false);
				primaryStage.show();
			}
			for(User u: users) {
				if(u.getUser().equals(new_user)) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/photoLibrary/view/admin.fxml"));
					UserController photoController = 
							loader.getController();
					photoController.start(primaryStage);
					AnchorPane root = (AnchorPane)loader.load();

					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("User Console");
					primaryStage.setResizable(false);
					primaryStage.show();
				}
			}
			incorrectInfoError("Incorrect Username", "No username in database");
		}
		
	}
}
