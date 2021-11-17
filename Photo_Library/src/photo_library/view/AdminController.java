package photo_library.view;
import javafx.collections.ObservableList;
import java.io.File;
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
import java.io.File;

public class AdminController extends PhotoLibController {
	@FXML Button logout;
	@FXML ListView<String> userList;
	@FXML TextField enterUsername;
	@FXML Button addUser;
	@FXML Button deleteUser;
	private ObservableList<String> usernames;
	
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Admin page loading");
		mainStage = primaryStage;
		usernames = FXCollections.observableArrayList();
		for(User u:PhotoLibrary.users) {
			usernames.add(u.getUser());
		}
		userList.setItems(usernames);
		userList.getSelectionModel().select(0);
	}
	
	public void addUser(ActionEvent e) throws Exception {
		if((Button)e.getSource() == addUser) {
			String temp = enterUsername.getText().trim();
			enterUsername.clear();
			boolean duplicate = false;
			if(!temp.isEmpty()) {
				System.out.println("checking for duplicates");
				ArrayList<String> tempList = new ArrayList<String>();
				for(int i = 0; i < usernames.size();i++) {
					tempList.add(usernames.get(i));
				}
				for(String s: tempList) {
					if(temp.equals(s)) {
						incorrectInfoError("duplicate username","User already exists");
						duplicate = true;
						break;
					}
				}
				if(duplicate) {
					return;
				}
				
					
					usernames.add(temp);
					PhotoLibrary.users.add(new User(temp));
				}
			else {
				incorrectInfoError("no username","No username inputted in text box. Please try again after typing a name.");
			}
		}
	}
	
	public void deleteUser(ActionEvent e) throws Exception{
		if((Button)e.getSource() == deleteUser) {
			int index = userList.getSelectionModel().getSelectedIndex();
			if(index==-1) {
				return;
			}
			userList.getItems().remove(index);
			String name = usernames.get(index);
			usernames.remove(index);
			User temp = PhotoLibrary.getUser(name);
			PhotoLibrary.users.remove(temp);
		}
	}
	
}
