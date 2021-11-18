package photos.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photos.app.*;
/**
 * Controller to initialize move photo page
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class MovePhotoController {
	private Stage currentStage;
	private Album previousAlbum;
	private Photo movingPhoto;
	private ObservableList<String> albumsInList;
	@FXML
	ListView<String> albumsListView;
	
	@FXML
	private void returnToPrevious() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/photoView.fxml"));
		VBox root = (VBox)loader.load();
		PhotoViewController photoViewPage = loader.getController();
		photoViewPage.start(currentStage, previousAlbum);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	
	@FXML
	private void copyPicture() {
		int index = albumsListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		if(albumsInList.get(index).equals(previousAlbum.getName()) || Photos.activeUser.searchUserAlbums(albumsInList.get(index)).getImagesInAlbum().contains(movingPhoto)) {
			// throw error because can't copy image to the same place it already is
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Copy");
			alert.setHeaderText("The photo you have chosen is already in the album. Please try again.");
			alert.showAndWait();
			return;
		}
		Album destinationAlbum = Photos.activeUser.searchUserAlbums(albumsInList.get(index));
		destinationAlbum.addImageToAlbum(movingPhoto);
	}
	
	@FXML
	private void movePicture() {
		int index = albumsListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		if(albumsInList.get(index).equals(previousAlbum.getName()) || Photos.activeUser.searchUserAlbums(albumsInList.get(index)).getImagesInAlbum().contains(movingPhoto)) {
			// throw error because can't move image to the same place it already is
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Move");
			alert.setHeaderText("The photo you have chosen is already in the album. Please try again.");
			alert.showAndWait();
			return;
		}
		Album destinationAlbum = Photos.activeUser.searchUserAlbums(albumsInList.get(index));
		destinationAlbum.addImageToAlbum(movingPhoto);
		previousAlbum.getImagesInAlbum().remove(movingPhoto);
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public MovePhotoController() {
		
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 * @param previousAlbum The album where the photo being moved/copied originated.
	 * @param movingPhoto The photo that will be either moved or copied to another album.
	 */
	public void start(Stage primaryStage, Album previousAlbum, Photo movingPhoto) {
		currentStage = primaryStage;
		this.previousAlbum=previousAlbum;
		this.movingPhoto = movingPhoto;
		
		//populate ListView
		albumsInList = FXCollections.observableArrayList();
		for(int i = 0; i<Photos.activeUser.getUserAlbums().size(); i++) {
			albumsInList.add(Photos.activeUser.getUserAlbums().get(i).getName());
		}
		albumsListView.setItems(albumsInList);
	}
}
