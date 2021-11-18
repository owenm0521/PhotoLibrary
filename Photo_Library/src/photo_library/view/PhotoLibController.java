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

/**
 * superclass controller for all other controllers 
 * 
 * @author Owen Morris
 * @author Ali Khan
 */
public abstract class PhotoLibController {
	@FXML Button logout;
	Stage mainStage;
	
	/**
	 * logs out of current user's account and returns to login page 
	 * @param e logout button
	 * @throws Exception
	 */
	public void logout(ActionEvent e) throws Exception{
		if ((Button)e.getSource() == logout) {
			System.out.println("Logging out");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photo_library/view/login.fxml"));
			System.out.println("starting to load");
			AnchorPane root = (AnchorPane)loader.load();
			System.out.println("loaded pane");
			loginController photoController = loader.getController();
			
			photoController.start(mainStage);

			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("Login");
			mainStage.setResizable(false);
			mainStage.show();
		}
	}
	
	/**
	 * generic error method for displaying errors in other controllers 
	 * @param content body of error message 
	 * @param title header of error message 
	 */
	public void incorrectInfoError(String content, String title) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.show();
	}
	
}
