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

public class AdminController extends PhotoLibController {
	@FXML Button exit;
	@FXML Button logout;
	@FXML ListView userList;
	@FXML TextField enterUsername;
	@FXML Button addUser;
	@FXML Button deleteUser;
	
	public void start(Stage primaryStage) throws Exception {
		System.out.print("Admin page loading");
		mainStage = primaryStage;
		Admin admin = new Admin();
		ArrayList<String> temp = new ArrayList<String>();
		for(User u:admin.getUsers()) {
			temp.add(u.getUser());
		}
		userList.setItems(FXCollections.observableArrayList(temp));
		if(temp.size() > 0) {
			temp.sort(null);
			userList.getSelectionModel().select(0);
		}
	}
	
	public void addSong(ActionEvent e) {
		if((Button)e.getSource() == addUser) {
			String temp = enterUsername.getText().trim();
			if(!temp.isEmpty()) {
				
			}
			else {
				incorrectInfoError("no username","No username inputted in text box. Please try again after typing a name.");
			}
		}
	}
	
}
