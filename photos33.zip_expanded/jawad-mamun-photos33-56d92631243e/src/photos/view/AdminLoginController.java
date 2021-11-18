package photos.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photos.app.Photos;
import photos.app.User;
/**
 * Controller to initialize when admin logs in
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class AdminLoginController {
	private Stage currentStage = null;
	private ObservableList<String> usersInList;
	@FXML
	ListView<String> usersListView;
	@FXML
	TextField newUsername;
	
	@FXML
	private void deleteUser() {
		int deletedUserIndex = usersListView.getSelectionModel().getSelectedIndex();
		if(deletedUserIndex==-1) return;
		User deletedUser = Photos.findUser(usersInList.get(deletedUserIndex));
		usersInList.remove(deletedUserIndex);
		Photos.applicationUsers.remove(deletedUser);
	}
	
	@FXML
	private void createNewUser() {
		// invalid username (empty string or taken username), throws alert
		if(newUsername.getText().equals("") || Photos.findUser(newUsername.getText())!=null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing));
			alert.setTitle("Invalid Username");
			alert.setHeaderText("The username you entered is not valid (either already taken or blank). Please try again.");
			alert.showAndWait();
		} else {
			Photos.applicationUsers.add(new User(newUsername.getText().toLowerCase()));
			usersInList.add(newUsername.getText().toLowerCase());
		}
	}
	
	@FXML
	private void logOut() throws IOException {
		// goes back to initial login page
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/loginPage.fxml"));
		VBox root = (VBox)loader.load();
		LoginPageController loginPage = loader.getController();
		loginPage.start(currentStage);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public AdminLoginController() {
		
	}
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 */
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		
		// populate listview
		usersInList = FXCollections.observableArrayList();
		for(int i = 0; i<Photos.applicationUsers.size(); i++) {
			usersInList.add(Photos.applicationUsers.get(i).getName());
		}
		usersListView.setItems(usersInList);
		
		// select the first item the first time the window opens
		usersListView.getSelectionModel().select(0);
	}
}
