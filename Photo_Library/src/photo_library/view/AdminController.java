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
	private Admin admin;
	private ArrayList<String> usernames;
	
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Admin page loading");
		mainStage = primaryStage;
		admin = new Admin();
		usernames = new ArrayList<String>();
		for(User u:admin.getUsers()) {
			usernames.add(u.getUser());
		}
		usernames.sort(null);
		userList.setItems(FXCollections.observableArrayList(usernames));
		if(usernames.size() > 0) {
			userList.getSelectionModel().select(0);
		}
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
				
					admin.addUser(temp);
					usernames.add(temp);
					usernames.sort(null);
					int index = usernames.indexOf(temp);
					ObservableList<String> newList = FXCollections.observableArrayList(usernames);
					userList.setItems(newList);
					userList.getSelectionModel().select(index);
					
				}
			else {
				incorrectInfoError("no username","No username inputted in text box. Please try again after typing a name.");
			}
		}
	}
	
	public void deleteUser(ActionEvent e) throws Exception{
		if((Button)e.getSource() == deleteUser) {
			int index = userList.getSelectionModel().getSelectedIndex();
			userList.getItems().remove(index);
			String name = usernames.get(index);
			admin.deleteUser(name);
			usernames.remove(index);
			File removal = new File("src/"+name +".txt");
			removal.delete();
		}
	}
	
}
