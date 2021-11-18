package photos.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photos.app.*;
/**
 * Controller to initialize create new user page
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class CreateNewUserController {
	private Stage currentStage = null;
	@FXML
	TextField inputtedNewUsername;
	
	@FXML
	private void createNewUser() throws IOException{
		// invalid username (empty string or taken username), throws alert
		if(inputtedNewUsername.getText().equals("") || Photos.findUser(inputtedNewUsername.getText())!=null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing));
			alert.setTitle("Invalid Username");
			alert.setHeaderText("The username you entered is not valid (either already taken or blank). Please try again.");
			alert.showAndWait();
		} else {
			Photos.applicationUsers.add(new User(inputtedNewUsername.getText().toLowerCase()));
			Photos.activeUser = Photos.findUser(inputtedNewUsername.getText().toLowerCase());
			// switches to Album View Page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					getClass().getResource("/photos/view/albumView.fxml"));
			VBox root = (VBox)loader.load();
			AlbumViewController albumViewPage = loader.getController();
			albumViewPage.start(currentStage);

			Scene scene = new Scene(root);
			currentStage.setScene(scene);
			currentStage.setResizable(false);
			currentStage.show();
		}
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public CreateNewUserController() {
		
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 */
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
	}
}
