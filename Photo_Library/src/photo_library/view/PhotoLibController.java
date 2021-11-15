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

public abstract class PhotoLibController {
	@FXML Button logout;
	
	public void logout(ActionEvent e, Stage primaryStage) throws Exception{
		if ((Button)e.getSource() == logout) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photoLibrary/view/login.fxml"));
			loginController photoController = 
					loader.getController();
			photoController.start(primaryStage);
			AnchorPane root = (AnchorPane)loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	}
	
	
	public void incorrectInfoError(String content, String title) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.show();
	}
	
}
