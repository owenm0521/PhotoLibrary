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
import javafx.stage.Window;
import photos.app.Photos;
/**
 * Controller to initialize log in page.
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class LoginPageController {
	private Stage currentStage = null;
	@FXML
	TextField inputtedUsername;
	
	@FXML
	private void login() throws IOException {
		// actions for user login
		if(inputtedUsername.getText().toLowerCase().equals("admin")) {
			// switches to Admin Page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					getClass().getResource("/photos/view/adminLogin.fxml"));
			VBox root = (VBox)loader.load();
			AdminLoginController adminLoginPage = loader.getController();
			adminLoginPage.start(currentStage);

			Scene scene = new Scene(root);
			currentStage.setScene(scene);
			currentStage.setResizable(false);
			currentStage.show();
		}
		else if(Photos.findUser(inputtedUsername.getText().toLowerCase())!=null) {
			// sets active user to current user
			Photos.activeUser = Photos.findUser(inputtedUsername.getText().toLowerCase());
			
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
		} else {
			// throws alert saying username is invalid
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing));
			alert.setTitle("Invalid Username");
			alert.setHeaderText("The username you entered is not in the system. Please try again.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void createNewUser() throws IOException {
		// switches to create new user page
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/createNewUser.fxml"));
		VBox root = (VBox)loader.load();
		CreateNewUserController createNewUserPage = loader.getController();
		createNewUserPage.start(currentStage);
		
		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public LoginPageController() {
		
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param mainStage The primary stage for the application where the application scene is set.
	 */
	public void start(Stage mainStage) {
		Photos.activeUser = null;
		currentStage = mainStage;
	}
	
}
