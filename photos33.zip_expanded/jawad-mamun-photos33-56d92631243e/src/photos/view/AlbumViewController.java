package photos.view;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photos.app.*;
/**
 * Controller to initialize album view page
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class AlbumViewController {
	private Stage currentStage = null;
	private ObservableList<String> albumsInList;
	private User activeUser = null;
	@FXML
	ListView<String> albumListView;
	@FXML
	Label albumName;
	@FXML
	Label numPhotos;
	@FXML
	Label dateRange;
	
	@FXML
	private void createAlbum() {
		// dialog popup to enter new album name
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(currentStage); 
		dialog.setTitle("Create Album");
		dialog.setHeaderText("Create Album");
		dialog.setContentText("Enter new album name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if(result.get().equals("") || activeUser.searchUserAlbums(result.get())!=null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Album Name");
				alert.setHeaderText("The album name you entered is not valid (either already taken or blank). Please try again.");
				alert.showAndWait();
			} else {
				activeUser.getUserAlbums().add(new Album(result.get()));
				albumsInList.add(result.get());
			}
		}
	}
	
	@FXML
	private void renameAlbum() {
		// dialog popup to rename album
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(currentStage); 
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("Rename Album");
		dialog.setContentText("Enter new album name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if(result.get().equals("") || activeUser.searchUserAlbums(result.get())!=null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Album Name");
				alert.setHeaderText("The album name you entered is not valid (either already taken or blank). Please try again.");
				alert.showAndWait();
				return;
			} else {
				int index = albumListView.getSelectionModel().getSelectedIndex();
				if (index == -1)
					return;
				Album selectedAlbum = null;
				// finds and designates selected album
				for(int i = 0; i<activeUser.getUserAlbums().size(); i++) {
					if(activeUser.getUserAlbums().get(i).getName().equals(albumsInList.get(index))) {
						selectedAlbum = activeUser.getUserAlbums().get(i);
					}
				}
				// changes name of selectedAlbum
				selectedAlbum.setName(result.get());
				// takes out previous name from observable list and adds new name
				albumsInList.remove(index);
				albumsInList.add(result.get());
				albumListView.getSelectionModel().select(result.get());
			}
		}
		
	}
	
	@FXML
	private void deleteAlbum() {
		int index = albumListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		// finds and deletes album from user's list of albums
		for(int i = 0; i<activeUser.getUserAlbums().size(); i++) {
			if(activeUser.getUserAlbums().get(i).getName().equals(albumsInList.get(index))) {
				activeUser.getUserAlbums().remove(i);
				break;
			}
		}
		// removes album from observable list as well
		albumsInList.remove(index);
		
		if (index == 0) {
			albumListView.getSelectionModel().select(index - 1);
		}
		albumListView.getSelectionModel().select(index);
		if (albumsInList.size() <= 0) {
			albumName.setText("Album Name: ");
			numPhotos.setText("Number of Photos: ");
			dateRange.setText("Date Range: ");
		}
	}
	
	@FXML
	private void openAlbum() throws IOException {
		int index = albumListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Album selectedAlbum = null;
		// finds and designates selected album
		for(int i = 0; i<activeUser.getUserAlbums().size(); i++) {
			if(activeUser.getUserAlbums().get(i).getName().equals(albumsInList.get(index))) {
				selectedAlbum = activeUser.getUserAlbums().get(i);
			}
		}
		// takes user to photo view page, passing the selected album as a parameter
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/photoView.fxml"));
		VBox root = (VBox)loader.load();
		PhotoViewController photoViewPage = loader.getController();
		photoViewPage.start(currentStage, selectedAlbum);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	@FXML
	private void searchForPhoto() throws IOException {
		// takes user to photo search page
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/photoSearch.fxml"));
		VBox root = (VBox)loader.load();
		PhotoSearchController photoSearchPage = loader.getController();
		photoSearchPage.start(currentStage);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
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
	public AlbumViewController() {
		
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 */
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		activeUser = Photos.activeUser;



		
		//populate ListView
		albumsInList = FXCollections.observableArrayList();
		for(int i = 0; i<activeUser.getUserAlbums().size(); i++) {
			albumsInList.add(activeUser.getUserAlbums().get(i).getName());
		}
		albumListView.setItems(albumsInList);
		
		// set listener for listview
		albumListView.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldVal, newVal) -> populateCurrentAlbum());
		
		// select the first item the first time the window opens
		albumListView.getSelectionModel().select(0);
		
	}
	
	/**
	 * Updates the specific album area with information from the user's selected album.
	 */
	public void populateCurrentAlbum() {
		int index = albumListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Album selectedAlbum = null;
		// finds and designates selected album
		for(int i = 0; i<activeUser.getUserAlbums().size(); i++) {
			if(activeUser.getUserAlbums().get(i).getName().equals(albumsInList.get(index))) {
				selectedAlbum = activeUser.getUserAlbums().get(i);
			}
		}
		// edits labels at bottom to show selected album's info
		albumName.setText("Album Name: " + selectedAlbum.getName());
		numPhotos.setText("Number of photos: " + selectedAlbum.getImagesInAlbum().size());
		if(selectedAlbum.getMinDate()==null) {
			dateRange.setText("Date Range: N/A");
		} else {
			dateRange.setText("Date Range: " + selectedAlbum.getMinDate() + " to " + selectedAlbum.getMaxDate());
		}
	}
}
